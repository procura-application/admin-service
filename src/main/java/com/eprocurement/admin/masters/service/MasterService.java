package com.eprocurement.admin.masters.service;

import com.eprocurement.admin.common.dto.PageRequestDto;
import com.eprocurement.admin.common.entity.PrefixConfigurationEntity;
import com.eprocurement.admin.common.exceptions.NotFoundResourceException;
import com.eprocurement.admin.common.repository.PrefixConfigurationRepository;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import com.eprocurement.admin.masters.dto.*;
import com.eprocurement.admin.masters.entity.*;
import com.eprocurement.admin.masters.enums.CategoryStatusEnum;
import com.eprocurement.admin.masters.mapper.*;
import com.eprocurement.admin.masters.repository.*;
import com.eprocurement.admin.masters.enums.MasterEntityTypeEnum;
import com.eprocurement.admin.security.entity.User;
import com.eprocurement.admin.security.repository.UserRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.eprocurement.admin.common.exceptions.DuplicateResourceException;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MasterService {
	
	private final BusinessUnitRepository businessUnitRepository;
	private final BusinessUnitMapper businessUnitMapper;
	private final PrefixConfigurationRepository prefixConfigurationRepository;
	private final MasterCategoryRepository masterCategoryRepository;
	private final MasterCategoryMapper masterCategoryMapper;
	private final MasterItemRepository masterItemRepository;
	private final MasterItemMapper masterItemMapper;
	private final MasterCurrencyRepository masterCurrencyRepository;
	private final MasterCurrencyMapper masterCurrencyMapper;
	private final MasterUnitRepository masterUnitRepository;
	private final MasterUnitMapper masterUnitMapper;
	private final CostCenterRepository costCenterRepository;
	private final CostCenterMapper costCenterMapper;
	private final UserRepository userRepository;
	
	
	public BusinessUnitResponseDto addBusinessUnit(BusinessUnitRequestDto businessUnitRequestDto) {
		
		if(businessUnitRepository.existsByBusinessUnitNameIgnoreCase(businessUnitRequestDto.getBusinessUnitName()))
			throw new DuplicateResourceException("Business Unit Already Exists");
		String managerId = businessUnitRequestDto.getBusinessUnitManagerId();
		if (businessUnitRepository.existsByBusinessUnitManager_UserId(managerId))
			throw new DuplicateResourceException("Business Unit Manager is already assigned");
		User businessUnitManager = userRepository.findById(managerId)
				.orElseThrow(() -> new NotFoundResourceException("Business Unit Manager Not Found"));

		String buCode = getMasterEntityCode(MasterEntityTypeEnum.BUSINESS_UNIT);
		BusinessUnit businessUnit = BusinessUnit.builder()
				.businessUnitDescription(businessUnitRequestDto.getBusinessUnitDescription())
				.businessUnitName(businessUnitRequestDto.getBusinessUnitName())
				.businessUnitManager(businessUnitManager)
				.businessUnitCode(buCode)
				.status(CommonStatusEnum.ACTIVE)
				.build();
        businessUnitRepository.save(businessUnit);
        return businessUnitMapper.toDto(businessUnit);
	}



	public BusinessUnitResponseDto updateBusinessUnit(BusinessUnitRequestDto businessUnitRequestDto,String id){
		Optional<BusinessUnit> businessUnitOptional = businessUnitRepository.
				findById(id);
		if(businessUnitOptional.isEmpty()){
			throw new NotFoundResourceException("Business Unit Does Not Exist");

		}
		BusinessUnit businessUnit = businessUnitOptional.get();
		String businessUnitName = businessUnitRequestDto.getBusinessUnitName();
		if (businessUnitName != null && !businessUnitName.isBlank()) {
			if (businessUnitRepository.existsByBusinessUnitNameIgnoreCaseAndIdNot(businessUnitName, id))
				throw new DuplicateResourceException("Business Unit Already Exists");
			businessUnit.setBusinessUnitName(businessUnitName);
		}
		String managerId = businessUnitRequestDto.getBusinessUnitManagerId();
		if (managerId != null && !managerId.isBlank()) {
			if (businessUnitRepository.existsByBusinessUnitManager_UserIdAndIdNot(managerId, id))
				throw new DuplicateResourceException("Business Unit Manager is already assigned");
			User businessUnitManager = userRepository.findById(managerId)
					.orElseThrow(() -> new NotFoundResourceException("Business Unit Manager Not Found"));
			businessUnit.setBusinessUnitManager(businessUnitManager);
		}

		if(businessUnitRequestDto.getBusinessUnitDescription()!=null && !businessUnitRequestDto.getBusinessUnitDescription().isEmpty()) businessUnit.setBusinessUnitDescription(businessUnitRequestDto.getBusinessUnitDescription());


		businessUnitRepository.save(businessUnit);

		return businessUnitMapper.toDto(businessUnit);
	}

	public void deleteBusinessUnit(String id){
		Optional<BusinessUnit> businessUnitOptional = businessUnitRepository.
				findById(id);
		if(businessUnitOptional.isEmpty()){
			throw new NotFoundResourceException("Business Unit Does Not Exist");

		}
		BusinessUnit businessUnit = businessUnitOptional.get();
		businessUnit.setStatus(CommonStatusEnum.INACTIVE);
		businessUnitRepository.save(businessUnit);
	}

	public Page<BusinessUnitResponseDto> getBusinessUnitList(PageRequestDto pageRequestDto){

		if(pageRequestDto==null) pageRequestDto = new PageRequestDto();

		if(pageRequestDto.getSort()==null || pageRequestDto.getSort().isEmpty()) pageRequestDto.setSort("businessUnitName");

		boolean search = false;
		Specification<BusinessUnit> spec = null;
		if(pageRequestDto.getSearch()!=null){
			search = true;
			String searchString = pageRequestDto.getSearch();

			spec = (root, query, cb) -> {
				Join<BusinessUnit, User> manager = root.join("businessUnitManager", JoinType.LEFT);
				return cb.or(
							cb.like(root.get("businessUnitName"), "%" + searchString + "%"),
							cb.like(root.get("businessUnitCode"), "%" + searchString + "%"),
							cb.like(cb.lower(manager.get("username")), "%" + searchString.toLowerCase() + "%"),
							cb.like(cb.lower(manager.get("email")), "%" + searchString.toLowerCase() + "%"),
							cb.like(root.get("businessUnitDescription"), "%" + searchString + "%")
					);
			};

		}


		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(),pageRequestDto.getPageSize(),
				Sort.by(Sort.Direction.DESC, "createdDate"));
		Page<BusinessUnit> businessUnitsPage = search ? businessUnitRepository.findAll( Specification.anyOf(spec),pageable) : businessUnitRepository.findAll(pageable);
		List<BusinessUnitResponseDto> businessUnitResponseDtos = businessUnitMapper.toDtoList(businessUnitsPage.getContent());
        return new PageImpl<>(businessUnitResponseDtos,
                businessUnitsPage.getPageable(),businessUnitsPage.getTotalElements());


	}


	public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto){


		if(masterCategoryRepository.findByMasterCategoryName(categoryRequestDto.getCategoryName()).isPresent())
			throw new DuplicateResourceException("Category Already exists");
		String categoryCode = getMasterEntityCode(MasterEntityTypeEnum.CATEGORY);

		MasterCategoryEntity masterCategoryEntity = MasterCategoryEntity.builder()
				.masterCategoryCode(categoryCode)
				.masterCategoryName(categoryRequestDto.getCategoryName())
				.businessUnit(businessUnitRepository.findById(categoryRequestDto.getBusinessUnitId())
						.orElseThrow(()-> new NotFoundResourceException("Business Unit Not Found")))
				.itemType(categoryRequestDto.getItemType())
				.status(CategoryStatusEnum.ACTIVE)
				.build();
		masterCategoryRepository.save(masterCategoryEntity);
		return masterCategoryMapper.toDto(masterCategoryEntity);

	}

	public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto,String masterCategoryId){

		MasterCategoryEntity masterCategoryEntity = masterCategoryRepository.findById(masterCategoryId).orElseThrow(
				()-> new NotFoundResourceException("Category Not Found"));
		if(!StringUtils.isBlank(categoryRequestDto.getCategoryName().trim())){
			if(masterCategoryRepository.existsByMasterCategoryNameAndMasterCategoryIdNot(categoryRequestDto.getCategoryName().trim(),masterCategoryEntity.getMasterCategoryId()))
				throw new DuplicateResourceException("Duplicate Category Name not allowed");
			masterCategoryEntity.setMasterCategoryName(categoryRequestDto.getCategoryName().trim());
		}
		if(Objects.nonNull(categoryRequestDto.getItemType())) masterCategoryEntity.setItemType(categoryRequestDto.getItemType());
		if(!StringUtils.isBlank(categoryRequestDto.getBusinessUnitId())){
			BusinessUnit businessUnit = businessUnitRepository.findById(categoryRequestDto.getBusinessUnitId()).orElseThrow(()-> new NotFoundResourceException("Business Unit Not Found"));
			masterCategoryEntity.setBusinessUnit(businessUnit);
		}

		masterCategoryRepository.save(masterCategoryEntity);

		return masterCategoryMapper.toDto(masterCategoryEntity);

	}

	public void deleteCategory(String masterCategoryId){
		MasterCategoryEntity masterCategoryEntity = masterCategoryRepository.findById(masterCategoryId).orElseThrow(()-> new NotFoundResourceException("Category Not Found"));
		masterCategoryEntity.setStatus(CategoryStatusEnum.INACTIVE);
		masterCategoryRepository.save(masterCategoryEntity);
	}

	public Page<CategoryResponseDto> getMasterCategoryList(PageRequestDto pageRequestDto){

		if(pageRequestDto==null) pageRequestDto = new PageRequestDto();

		if(pageRequestDto.getSort()==null || pageRequestDto.getSort().isEmpty()) pageRequestDto.setSort("createdDate");

		boolean search = false;
		Specification<MasterCategoryEntity> spec = null;
		if(pageRequestDto.getSearch()!=null){
			search = true;
			String searchString = pageRequestDto.getSearch().trim().toLowerCase();

			spec = (root, query, cb) -> {
				Join<MasterCategoryEntity, BusinessUnit> businessUnit =
						root.join("businessUnit", JoinType.LEFT);
				return cb.or(cb.like(root.get("masterCategoryCode"), "%" + searchString + "%"),
						cb.like(root.get("masterCategoryName"), "%" + searchString + "%"),
						cb.like(root.get("itemType"), "%" + searchString + "%"),
						cb.like(businessUnit.get("businessUnitName"), "%" + searchString + "%"));
			};

		}


		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(),pageRequestDto.getPageSize(),
				Sort.by(Sort.Direction.DESC, pageRequestDto.getSort()));
		Page<MasterCategoryEntity> categoryPage = search ? masterCategoryRepository.findAll( Specification.anyOf(spec),pageable) : masterCategoryRepository.findAll(pageable);
		List<CategoryResponseDto> categoryResponseDtoList = masterCategoryMapper.toDtoList(categoryPage.getContent());
		return new PageImpl<>(categoryResponseDtoList,
				categoryPage.getPageable(),categoryPage.getTotalElements());


	}

	public MasterItemResponseDto addMasterItem(MasterItemRequestDto masterItemRequestDto){


		MasterCategoryEntity masterCategoryEntity = masterCategoryRepository.findById(masterItemRequestDto.getMasterItemCategoryId())
				.orElseThrow(()-> new NotFoundResourceException("Master Category Not Found"));
		if(masterItemRepository.existsByMasterItemNameIgnoreCaseAndMasterItemCategory_BusinessUnit_Id(
				masterItemRequestDto.getMasterItemName(),masterCategoryEntity.getBusinessUnit().getId()))
			throw new DuplicateResourceException("Please Select New Item Name as Item Name already exists for this Business Unit");
		MasterItemEntity masterItemEntity = MasterItemEntity.builder()
				.masterItemId(UUID.randomUUID().toString())
				.masterItemCode(getMasterEntityCode(MasterEntityTypeEnum.MASTER_ITEM))
				.masterItemName(masterItemRequestDto.getMasterItemName())
				.masterItemDescription(masterItemRequestDto.getMasterItemDescription())
				.masterItemCategory(masterCategoryEntity)
                .status(CommonStatusEnum.ACTIVE)
				.build();
		masterItemRepository.save(masterItemEntity);
		return masterItemMapper.toDto(masterItemEntity);
	}

	public MasterItemResponseDto updateMasterItem(String masterItemId,MasterItemRequestDto masterItemRequestDto){
		MasterItemEntity masterItemEntity = masterItemRepository.findById(masterItemId).orElseThrow(()-> new NotFoundResourceException("Master Item not found for the provided ID:"+masterItemId));
		String masterItemName = masterItemRequestDto.getMasterItemName();
		String masterItemDescription = masterItemRequestDto.getMasterItemDescription();
		String masterItemCategory = masterItemRequestDto.getMasterItemCategoryId();
		MasterCategoryEntity masterCategoryEntity = masterItemEntity.getMasterItemCategory();
		if(!StringUtils.isBlank(masterItemName)) masterItemEntity.setMasterItemName(masterItemName.trim());
		if(!StringUtils.isBlank(masterItemDescription)) masterItemEntity.setMasterItemDescription(masterItemDescription.trim());
		if(!StringUtils.isBlank(masterItemCategory)){
			masterCategoryEntity = masterCategoryRepository.findById(masterItemRequestDto.getMasterItemCategoryId())
					.orElseThrow(()-> new NotFoundResourceException("Master Category Not Found"));
			masterItemEntity.setMasterItemCategory(masterCategoryEntity);
		}
		if(masterItemRepository.existsByMasterItemNameIgnoreCaseAndMasterItemCategory_BusinessUnit_Id(
				masterItemRequestDto.getMasterItemName(),masterCategoryEntity.getBusinessUnit().getId()))
			throw new DuplicateResourceException("Please Select New Item Name as Item Name already exists for this Business Unit");
		masterItemRepository.save(masterItemEntity);
		return masterItemMapper.toDto(masterItemEntity);

	}

	public void deleteMasterItem(String masterItemId){
		MasterItemEntity masterItemEntity = masterItemRepository.findById(masterItemId).orElseThrow(()-> new NotFoundResourceException("Msster Item Not Found for given ID:"+masterItemId));
        masterItemEntity.setStatus(CommonStatusEnum.INACTIVE);
        masterItemRepository.save(masterItemEntity);
	}

	public Page<MasterItemResponseDto> getMasterItemList(PageRequestDto pageRequestDto){
		boolean search = false;
		Specification<MasterItemEntity> spec = null;
		if(pageRequestDto.getSearch()!=null){
			search = true;
			String searchString = pageRequestDto.getSearch().trim().toLowerCase();
			spec = (root, query, cb) -> {
			Join<MasterItemEntity,MasterCategoryEntity> categoryEntity = root.join("masterItemCategory",JoinType.LEFT);
			Join<MasterCategoryEntity,BusinessUnit> businessUnit = categoryEntity.join("businessUnit",JoinType.LEFT);
			return cb.or(
				cb.like(root.get("masterItemCode"),"%"+searchString+"%"),
					cb.like(root.get("masterItemName"),"%"+searchString+"%"),
					cb.like(root.get("masterItemDescription"),"%"+searchString+"%"),
					cb.like(categoryEntity.get("masterCategoryCode"),"%"+searchString+"%"),
					cb.like(categoryEntity.get("masterCategoryName"),"%"+searchString+"%"),
					cb.like(businessUnit.get("businessUnitCode"),"%"+searchString+"%"),
					cb.like(businessUnit.get("businessUnitName"),"%"+searchString+"%")
			);
			};
		}

		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(),pageRequestDto.getPageSize(),
				Sort.by( Sort.Direction.DESC, pageRequestDto.getSort())
				);
		Page<MasterItemEntity> masterItemEntityPage = search ? masterItemRepository.findAll(Specification.anyOf(spec),pageable)
				: masterItemRepository.findAll(pageable);
		List<MasterItemResponseDto> masterItemResponseDtos = masterItemMapper.toDtoList(masterItemEntityPage.getContent());
		return new PageImpl<>(masterItemResponseDtos,pageable,masterItemEntityPage.getTotalElements());

	}

	public MasterCurrencyResponseDto addCurrency(MasterCurrencyAddRequestDto masterCurrencyAddRequestDto){

		if(masterCurrencyRepository.existsById(masterCurrencyAddRequestDto.getMasterCurrencyCode()))
			throw new DuplicateResourceException("Currency Code Already Exists");
		if(masterCurrencyRepository.findByMasterCurrencyName(masterCurrencyAddRequestDto.getMasterCurrencyName()).isPresent())
			throw new DuplicateResourceException("Currency Name Already Exists");
		MasterCurrencyEntity masterCurrencyEntity = masterCurrencyMapper.fromAddRequestToEntity(masterCurrencyAddRequestDto);
		masterCurrencyEntity.setStatus(CommonStatusEnum.ACTIVE);
		masterCurrencyRepository.save(masterCurrencyEntity);
		return masterCurrencyMapper.toDto(masterCurrencyEntity);
	}

	public MasterCurrencyResponseDto updateCurrency(String currencyCode,String currencyName){
		MasterCurrencyEntity masterCurrencyEntity = masterCurrencyRepository.findById(currencyCode).orElseThrow(()->
				new NotFoundResourceException("Currency Not Found"));

		if(masterCurrencyRepository.findByMasterCurrencyName(currencyName).isPresent())
			throw new DuplicateResourceException("Currency Name Already Exists");
		masterCurrencyEntity.setMasterCurrencyName(currencyName);
		masterCurrencyRepository.save(masterCurrencyEntity);
		return masterCurrencyMapper.toDto(masterCurrencyEntity);

	}

	public void deleteCurrency(String currencyCode){
		MasterCurrencyEntity masterCurrencyEntity = masterCurrencyRepository.findById(currencyCode).orElseThrow(()->
				new NotFoundResourceException("Currency Not Found"));
		masterCurrencyEntity.setStatus(CommonStatusEnum.INACTIVE);
		masterCurrencyRepository.save(masterCurrencyEntity);
	}

	public Page<MasterCurrencyResponseDto> getMasterCurrencyList(PageRequestDto pageRequestDto){
		boolean search = false;
		Specification<MasterCurrencyEntity> spec = null;
		if(pageRequestDto.getSearch()!=null){
			search = true;
			String searchString = pageRequestDto.getSearch().trim().toLowerCase();
			spec = (root,query,cb)->{
			return cb.or(
					cb.like(root.get("masterCurrencyCode"),"%"+searchString+"%"),
					cb.like(root.get("masterCurrencyName"),"%"+searchString+"%")
			);
			};

		}
		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(),
				pageRequestDto.getPageSize(),Sort.by(Sort.Direction.DESC, pageRequestDto.getSort()));
		Page<MasterCurrencyEntity> masterCurrencyEntityPage = search ? masterCurrencyRepository.findAll(Specification.anyOf(spec),pageable)
				: masterCurrencyRepository.findAll(pageable);
		List<MasterCurrencyResponseDto> masterCurrencyResponseDtoList = masterCurrencyMapper.toDtoList(masterCurrencyEntityPage.getContent());
		return new PageImpl<>(masterCurrencyResponseDtoList,pageable,masterCurrencyEntityPage.getTotalElements());
	}

	public MasterUnitResponseDto addMasterUnit(MasterUnitRequestDto masterUnitRequestDto){

		if(masterUnitRepository.existsById(masterUnitRequestDto.getMasterUnitCode()))
			throw new DuplicateResourceException("Master Unit Code Already Exists");
		if(masterUnitRepository.findByMasterUnitName(masterUnitRequestDto.getMasterUnitName()).isPresent())
			throw new DuplicateResourceException("Master Unit Name Already Exists");
		MasterUnitEntity masterUnitEntity = masterUnitMapper.toEntity(masterUnitRequestDto);
		masterUnitEntity.setStatus(CommonStatusEnum.ACTIVE);
		masterUnitRepository.save(masterUnitEntity);
		return masterUnitMapper.toDto(masterUnitEntity);
	}

	public MasterUnitResponseDto updateMasterUnit(MasterUnitRequestDto masterUnitRequestDto){
		MasterUnitEntity masterUnitEntity = masterUnitRepository.findById(masterUnitRequestDto.getMasterUnitCode())
				.orElseThrow(()-> new NotFoundResourceException("Master Unit Not Found"));

		if(masterUnitRepository.findByMasterUnitName(masterUnitRequestDto.getMasterUnitName()).isPresent())
			throw new DuplicateResourceException("Master Unit Name Already Exists");
		masterUnitEntity.setMasterUnitName(masterUnitRequestDto.getMasterUnitName());
		masterUnitRepository.save(masterUnitEntity);
		return masterUnitMapper.toDto(masterUnitEntity);
	}

	public void deleteMasterUnit(String masterUnitCode){
		MasterUnitEntity masterUnitEntity = masterUnitRepository.findById(masterUnitCode)
				.orElseThrow(()-> new NotFoundResourceException("Master Unit Not Found"));
		masterUnitEntity.setStatus(CommonStatusEnum.INACTIVE);
		masterUnitRepository.save(masterUnitEntity);
	}

	public Page<MasterUnitResponseDto> getMasterUnitList(PageRequestDto pageRequestDto){
		boolean search = false;
		Specification<MasterUnitEntity> spec = null;
		if(pageRequestDto.getSearch()!=null){
			search = true;
			String searchString = pageRequestDto.getSearch();
			spec = (root,query,cb)->{
				return cb.or(
						cb.like(root.get("masterUnitCode"),"%"+searchString+"%"),
						cb.like(root.get("masterUnitName"),"%"+searchString+"%")
				);
			};
		}

		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber() ,pageRequestDto.getPageSize(),
				Sort.by(Sort.Direction.DESC,pageRequestDto.getSort()));
		Page<MasterUnitEntity> masterUnitEntityPage = search ? masterUnitRepository.findAll(Specification.anyOf(spec),pageable):
				masterUnitRepository.findAll(pageable);
		List<MasterUnitResponseDto> masterUnitResponseDtos = masterUnitMapper.toDtoList(masterUnitEntityPage.getContent());
		return new PageImpl<>(masterUnitResponseDtos,pageable,masterUnitEntityPage.getTotalElements());
	}


	private String getMasterEntityCode(MasterEntityTypeEnum masterEntityTypeEnum) {
		PrefixConfigurationEntity prefixConfigurationEntity = prefixConfigurationRepository.findFirstByOrderByPrefixConfigurationIdAsc()
				.orElseThrow(()->new NotFoundResourceException("No Prefix Configuration was found"));
		String code = null;
		if(masterEntityTypeEnum.equals(MasterEntityTypeEnum.BUSINESS_UNIT)){
			Long buCodeSeq = businessUnitRepository.getBusinessUnitCodeSeq();
			String buCodePrefix = prefixConfigurationEntity.getBusinessUnitCodePrefix();
			code = (StringUtils.isBlank(buCodePrefix)? "BU" : buCodePrefix) + "-" + buCodeSeq;
		}
		else if(MasterEntityTypeEnum.CATEGORY.equals(masterEntityTypeEnum)){
			Long categoryCodeSeq = masterCategoryRepository.getMasterCategorySeq();
			String categoryCodePrefix = prefixConfigurationEntity.getMasterCategoryCodePrefix();
			code = (StringUtils.isBlank(categoryCodePrefix) ? "CAT": categoryCodePrefix) + "-" + categoryCodeSeq;
		}
		else if(MasterEntityTypeEnum.MASTER_ITEM.equals(masterEntityTypeEnum)){
			Long masterItemCodeSeq = masterItemRepository.getMasterItemSeq();
			String masterItemCodePrefix  = prefixConfigurationEntity.getMasterItemCodePrefix();
			code = (StringUtils.isBlank(masterItemCodePrefix) ? "ITM": masterItemCodePrefix) + "-" + masterItemCodeSeq;
		}
		else if(MasterEntityTypeEnum.PROJECT.equals(masterEntityTypeEnum)){
			Long projectCodeSeq = projectRepository.getProjectCodeSeq();
			String projectCodePrefix = prefixConfigurationEntity.getProjectCodePrefix();
			code = (StringUtils.isBlank(projectCodePrefix) ? "PRJ": projectCodePrefix) + "-" + projectCodeSeq;
		}
		else if(MasterEntityTypeEnum.WBS.equals(masterEntityTypeEnum)){
			Long wbsCodeSeq = wbsRepository.getWbsCodeSeq();
			String wbsCodePrefix = prefixConfigurationEntity.getWbsCodePrefix();
			code = (StringUtils.isBlank(wbsCodePrefix) ? "WBS": wbsCodePrefix) + "-" + wbsCodeSeq;
		}
		else if(MasterEntityTypeEnum.BUDGET.equals(masterEntityTypeEnum)){
			Long budgetCodeSeq = budgetRepository.getBudgetCodeSeq();
			String budgetCodePrefix = prefixConfigurationEntity.getBudgetCodePrefix();
			code = (StringUtils.isBlank(budgetCodePrefix) ? "BUD": budgetCodePrefix) + "-" + budgetCodeSeq;
		}
		else if(MasterEntityTypeEnum.DELIVERY_LOCATION.equals(masterEntityTypeEnum)){
			Long deliveryLocationCodeSeq = deliveryLocationRepository.getDeliveryLocationCodeSeq();
			String deliveryLocationCodePrefix = prefixConfigurationEntity.getDeliveryLocationCodePrefix();
			code = (StringUtils.isBlank(deliveryLocationCodePrefix) ? "DL": deliveryLocationCodePrefix) + "-" + deliveryLocationCodeSeq;
		}

		return code;
	}

	public CostCenterResponseDto addCostCenter(CostCenterRequestDto request) {
		String name = request.getCostCenterName().trim();
		if (costCenterRepository.existsByCostCenterNameIgnoreCase(name))
			throw new DuplicateResourceException("Cost Center Already Exists");
		BusinessUnit businessUnit = businessUnitRepository.findById(request.getBusinessUnitId())
				.orElseThrow(() -> new NotFoundResourceException("Business Unit Not Found"));
		CostCenterEntity costCenter = CostCenterEntity.builder()
				.costCenterCode("CC-" + costCenterRepository.getCostCenterCodeSeq())
				.costCenterName(name)
				.description(request.getDescription())
				.businessUnit(businessUnit)
				.effectiveFromDate(request.getEffectiveFromDate())
				.effectiveToDate(request.getEffectiveToDate())
				.status(CommonStatusEnum.ACTIVE)
				.build();
		return costCenterMapper.toDto(costCenterRepository.save(costCenter));
	}

	public CostCenterResponseDto updateCostCenter(String id, CostCenterRequestDto request) {
		CostCenterEntity costCenter = costCenterRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("Cost Center Not Found"));
		String name = request.getCostCenterName().trim();
		if (costCenterRepository.existsByCostCenterNameIgnoreCaseAndIdNot(name, id))
			throw new DuplicateResourceException("Cost Center Already Exists");
		BusinessUnit businessUnit = businessUnitRepository.findById(request.getBusinessUnitId())
				.orElseThrow(() -> new NotFoundResourceException("Business Unit Not Found"));
		costCenter.setCostCenterName(name);
		costCenter.setDescription(request.getDescription());
		costCenter.setBusinessUnit(businessUnit);
		costCenter.setEffectiveFromDate(request.getEffectiveFromDate());
		costCenter.setEffectiveToDate(request.getEffectiveToDate());
		return costCenterMapper.toDto(costCenterRepository.save(costCenter));
	}

	public void deleteCostCenter(String id) {
		CostCenterEntity costCenter = costCenterRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("Cost Center Not Found"));
		costCenter.setStatus(CommonStatusEnum.INACTIVE);
		costCenterRepository.save(costCenter);
	}

	public Page<CostCenterResponseDto> getCostCenterList(PageRequestDto pageRequestDto) {
		if (pageRequestDto == null) pageRequestDto = new PageRequestDto();
		if (pageRequestDto.getSort() == null || pageRequestDto.getSort().isBlank())
			pageRequestDto.setSort("createdDate");

		Specification<CostCenterEntity> spec = null;
		boolean search = pageRequestDto.getSearch() != null && !pageRequestDto.getSearch().isBlank();
		if (search) {
			String searchString = "%" + pageRequestDto.getSearch().trim().toLowerCase() + "%";
			spec = (root, query, cb) -> {
				Join<CostCenterEntity, BusinessUnit> businessUnit = root.join("businessUnit", JoinType.LEFT);
				return cb.or(
						cb.like(cb.lower(root.get("costCenterCode")), searchString),
						cb.like(cb.lower(root.get("costCenterName")), searchString),
						cb.like(cb.lower(root.get("description")), searchString),
						cb.like(cb.lower(businessUnit.get("businessUnitName")), searchString),
						cb.like(cb.lower(businessUnit.get("businessUnitCode")), searchString));
			};
		}

		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(), pageRequestDto.getPageSize(),
				Sort.by(Sort.Direction.DESC, pageRequestDto.getSort()));
		Page<CostCenterEntity> costCenters = search
				? costCenterRepository.findAll(Specification.anyOf(spec), pageable)
				: costCenterRepository.findAll(pageable);
		return new PageImpl<>(costCenterMapper.toDtoList(costCenters.getContent()),
				costCenters.getPageable(), costCenters.getTotalElements());
	}

	private final ProjectRepository projectRepository;
	private final ProjectMapper projectMapper;

	public ProjectResponseDto addProject(ProjectCreateRequestDto request) {
		String projectName = request.getProjectName().trim();
		if (projectRepository.existsByProjectNameIgnoreCase(projectName))
			throw new DuplicateResourceException("Project Already Exists");
		BusinessUnit businessUnit = businessUnitRepository.findById(request.getBusinessUnitId())
				.orElseThrow(() -> new NotFoundResourceException("Business Unit Not Found"));
		ProjectEntity project = ProjectEntity.builder()
				.projectCode(getMasterEntityCode(MasterEntityTypeEnum.PROJECT))
				.projectName(projectName)
				.description(request.getDescription())
				.businessUnit(businessUnit)
				.startDate(request.getStartDate())
				.endDate(request.getEndDate())
				.status(CommonStatusEnum.ACTIVE)
				.build();
		return projectMapper.toDto(projectRepository.save(project));
	}

	public ProjectResponseDto updateProject(String id, ProjectUpdateRequestDto request) {
		ProjectEntity project = projectRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("Project Not Found"));

		String projectName = request.getProjectName();
		if (projectName != null && !projectName.isBlank()) {
			projectName = projectName.trim();
			if (projectRepository.existsByProjectNameIgnoreCaseAndIdNot(projectName, id))
				throw new DuplicateResourceException("Project Already Exists");
			project.setProjectName(projectName);
		}
		if (request.getDescription() != null)
			project.setDescription(request.getDescription());
		if (request.getBusinessUnitId() != null && !request.getBusinessUnitId().isBlank()) {
			BusinessUnit businessUnit = businessUnitRepository.findById(request.getBusinessUnitId())
					.orElseThrow(() -> new NotFoundResourceException("Business Unit Not Found"));
			project.setBusinessUnit(businessUnit);
		}

		LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : project.getStartDate();
		LocalDate endDate = request.getEndDate() != null ? request.getEndDate() : project.getEndDate();
		if (endDate != null && endDate.isBefore(startDate))
			throw new IllegalArgumentException("End date cannot be before start date");
		if (request.getStartDate() != null)
			project.setStartDate(request.getStartDate());
		if (request.getEndDate() != null)
			project.setEndDate(request.getEndDate());
		if (request.getStatus() != null)
			project.setStatus(request.getStatus());

		return projectMapper.toDto(projectRepository.save(project));
	}

	public void deleteProject(String id) {
		ProjectEntity project = projectRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("Project Not Found"));
		project.setStatus(CommonStatusEnum.INACTIVE);
		projectRepository.save(project);
	}

	public Page<ProjectResponseDto> getProjectList(PageRequestDto pageRequestDto) {
		if (pageRequestDto == null)
			pageRequestDto = new PageRequestDto();
		if (pageRequestDto.getSort() == null || pageRequestDto.getSort().isBlank())
			pageRequestDto.setSort("createdDate");

		Specification<ProjectEntity> spec = null;
		boolean search = pageRequestDto.getSearch() != null && !pageRequestDto.getSearch().isBlank();
		if (search) {
			String searchString = "%" + pageRequestDto.getSearch().trim().toLowerCase() + "%";
			spec = (root, query, cb) -> {
				Join<ProjectEntity, BusinessUnit> businessUnit = root.join("businessUnit", JoinType.LEFT);
				return cb.or(
						cb.like(cb.lower(root.get("projectCode")), searchString),
						cb.like(cb.lower(root.get("projectName")), searchString),
						cb.like(cb.lower(root.get("description")), searchString),
						cb.like(cb.lower(businessUnit.get("businessUnitName")), searchString),
						cb.like(cb.lower(businessUnit.get("businessUnitCode")), searchString));
			};
		}

		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(), pageRequestDto.getPageSize(),
				Sort.by(Sort.Direction.DESC, pageRequestDto.getSort()));
		Page<ProjectEntity> projects = search
				? projectRepository.findAll(Specification.anyOf(spec), pageable)
				: projectRepository.findAll(pageable);
		return new PageImpl<>(projectMapper.toDtoList(projects.getContent()),
				projects.getPageable(), projects.getTotalElements());
	}

	private final WbsRepository wbsRepository;
	private final WbsMapper wbsMapper;

	public WbsResponseDto addWbs(WbsCreateRequestDto request) {
		String wbsName = request.getWbsName().trim();
		if (wbsRepository.existsByWbsNameIgnoreCaseAndProject_Id(wbsName, request.getProjectId()))
			throw new DuplicateResourceException("WBS Name Already Exists In This Project");
		ProjectEntity project = projectRepository.findById(request.getProjectId())
				.orElseThrow(() -> new NotFoundResourceException("Project Not Found"));
		WbsEntity wbs = WbsEntity.builder()
				.wbsCode(getMasterEntityCode(MasterEntityTypeEnum.WBS))
				.wbsName(wbsName)
				.wbsDescription(request.getWbsDescription())
				.project(project)
				.startDate(request.getStartDate())
				.endDate(request.getEndDate())
				.status(CommonStatusEnum.ACTIVE)
				.build();
		return wbsMapper.toDto(wbsRepository.save(wbs));
	}

	public WbsResponseDto updateWbs(String id, WbsUpdateRequestDto request) {
		WbsEntity wbs = wbsRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("WBS Not Found"));

		ProjectEntity project = wbs.getProject();
		if (request.getProjectId() != null && !request.getProjectId().isBlank()) {
			project = projectRepository.findById(request.getProjectId())
					.orElseThrow(() -> new NotFoundResourceException("Project Not Found"));
		}
		String wbsName = request.getWbsName();
		if (wbsName != null && !wbsName.isBlank())
			wbsName = wbsName.trim();
		else
			wbsName = wbs.getWbsName();
		if (wbsRepository.existsByWbsNameIgnoreCaseAndProject_IdAndIdNot(wbsName, project.getId(), id))
			throw new DuplicateResourceException("WBS Name Already Exists In This Project");

		LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : wbs.getStartDate();
		LocalDate endDate = request.getEndDate() != null ? request.getEndDate() : wbs.getEndDate();
		if (endDate != null && endDate.isBefore(startDate))
			throw new IllegalArgumentException("End date cannot be before start date");

		wbs.setWbsName(wbsName);
		if (request.getWbsDescription() != null)
			wbs.setWbsDescription(request.getWbsDescription());
		wbs.setProject(project);
		if (request.getStartDate() != null)
			wbs.setStartDate(request.getStartDate());
		if (request.getEndDate() != null)
			wbs.setEndDate(request.getEndDate());
		if (request.getStatus() != null)
			wbs.setStatus(request.getStatus());
		return wbsMapper.toDto(wbsRepository.save(wbs));
	}

	public void deleteWbs(String id) {
		WbsEntity wbs = wbsRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("WBS Not Found"));
		wbs.setStatus(CommonStatusEnum.INACTIVE);
		wbsRepository.save(wbs);
	}

	public Page<WbsResponseDto> getWbsList(PageRequestDto pageRequestDto) {
		if (pageRequestDto == null)
			pageRequestDto = new PageRequestDto();
		if (pageRequestDto.getSort() == null || pageRequestDto.getSort().isBlank())
			pageRequestDto.setSort("createdDate");

		Specification<WbsEntity> spec = null;
		boolean search = pageRequestDto.getSearch() != null && !pageRequestDto.getSearch().isBlank();
		if (search) {
			String searchString = "%" + pageRequestDto.getSearch().trim().toLowerCase() + "%";
			spec = (root, query, cb) -> {
				Join<WbsEntity, ProjectEntity> project = root.join("project", JoinType.LEFT);
				return cb.or(
						cb.like(cb.lower(root.get("wbsCode")), searchString),
						cb.like(cb.lower(root.get("wbsName")), searchString),
						cb.like(cb.lower(root.get("wbsDescription")), searchString),
						cb.like(cb.lower(project.get("projectCode")), searchString),
						cb.like(cb.lower(project.get("projectName")), searchString));
			};
		}

		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(), pageRequestDto.getPageSize(),
				Sort.by(Sort.Direction.DESC, pageRequestDto.getSort()));
		Page<WbsEntity> wbsPage = search
				? wbsRepository.findAll(Specification.anyOf(spec), pageable)
				: wbsRepository.findAll(pageable);
		return new PageImpl<>(wbsMapper.toDtoList(wbsPage.getContent()),
				wbsPage.getPageable(), wbsPage.getTotalElements());
	}

	private final BudgetRepository budgetRepository;
	private final BudgetMapper budgetMapper;

	public BudgetResponseDto addBudget(BudgetCreateRequestDto request) {
		String budgetName = request.getBudgetName().trim();
		if (budgetRepository.existsByBudgetNameIgnoreCaseAndWbs_Id(budgetName, request.getWbsId()))
			throw new DuplicateResourceException("Budget Name Already Exists In This WBS");
		WbsEntity wbs = wbsRepository.findById(request.getWbsId())
				.orElseThrow(() -> new NotFoundResourceException("WBS Not Found"));
		MasterCurrencyEntity currency = masterCurrencyRepository.findById(request.getCurrencyCode())
				.orElseThrow(() -> new NotFoundResourceException("Currency Not Found"));
		BudgetEntity budget = BudgetEntity.builder()
				.budgetCode(getMasterEntityCode(MasterEntityTypeEnum.BUDGET))
				.budgetName(budgetName)
				.budgetDescription(request.getBudgetDescription())
				.wbs(wbs)
				.currency(currency)
				.allocatedAmount(request.getAllocatedAmount())
				.consumedAmount(java.math.BigDecimal.ZERO)
				.startDate(request.getStartDate())
				.endDate(request.getEndDate())
				.status(CommonStatusEnum.ACTIVE)
				.build();
		return budgetMapper.toDto(budgetRepository.save(budget));
	}

	public BudgetResponseDto updateBudget(String id, BudgetUpdateRequestDto request) {
		BudgetEntity budget = budgetRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("Budget Not Found"));

		WbsEntity wbs = budget.getWbs();
		if (request.getWbsId() != null && !request.getWbsId().isBlank()) {
			wbs = wbsRepository.findById(request.getWbsId())
					.orElseThrow(() -> new NotFoundResourceException("WBS Not Found"));
		}
		String budgetName = request.getBudgetName();
		if (budgetName != null && !budgetName.isBlank())
			budgetName = budgetName.trim();
		else
			budgetName = budget.getBudgetName();
		if (budgetRepository.existsByBudgetNameIgnoreCaseAndWbs_IdAndIdNot(budgetName, wbs.getId(), id))
			throw new DuplicateResourceException("Budget Name Already Exists In This WBS");

		LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : budget.getStartDate();
		LocalDate endDate = request.getEndDate() != null ? request.getEndDate() : budget.getEndDate();
		if (endDate != null && endDate.isBefore(startDate))
			throw new IllegalArgumentException("End date cannot be before start date");

		budget.setBudgetName(budgetName);
		if (request.getBudgetDescription() != null)
			budget.setBudgetDescription(request.getBudgetDescription());
		budget.setWbs(wbs);
		if (request.getCurrencyCode() != null && !request.getCurrencyCode().isBlank()) {
			MasterCurrencyEntity currency = masterCurrencyRepository.findById(request.getCurrencyCode())
					.orElseThrow(() -> new NotFoundResourceException("Currency Not Found"));
			budget.setCurrency(currency);
		}
		if (request.getAllocatedAmount() != null)
			budget.setAllocatedAmount(request.getAllocatedAmount());
		if (request.getStartDate() != null)
			budget.setStartDate(request.getStartDate());
		if (request.getEndDate() != null)
			budget.setEndDate(request.getEndDate());
		if (request.getStatus() != null)
			budget.setStatus(request.getStatus());
		return budgetMapper.toDto(budgetRepository.save(budget));
	}

	public void deleteBudget(String id) {
		BudgetEntity budget = budgetRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("Budget Not Found"));
		budget.setStatus(CommonStatusEnum.INACTIVE);
		budgetRepository.save(budget);
	}

	public Page<BudgetResponseDto> getBudgetList(PageRequestDto pageRequestDto) {
		if (pageRequestDto == null)
			pageRequestDto = new PageRequestDto();
		if (pageRequestDto.getSort() == null || pageRequestDto.getSort().isBlank())
			pageRequestDto.setSort("createdDate");

		Specification<BudgetEntity> spec = null;
		boolean search = pageRequestDto.getSearch() != null && !pageRequestDto.getSearch().isBlank();
		if (search) {
			String searchString = "%" + pageRequestDto.getSearch().trim().toLowerCase() + "%";
			spec = (root, query, cb) -> {
				Join<BudgetEntity, WbsEntity> wbs = root.join("wbs", JoinType.LEFT);
				Join<WbsEntity, ProjectEntity> project = wbs.join("project", JoinType.LEFT);
				Join<BudgetEntity, MasterCurrencyEntity> currency = root.join("currency", JoinType.LEFT);
				return cb.or(
						cb.like(cb.lower(root.get("budgetCode")), searchString),
						cb.like(cb.lower(root.get("budgetName")), searchString),
						cb.like(cb.lower(root.get("budgetDescription")), searchString),
						cb.like(cb.lower(wbs.get("wbsCode")), searchString),
						cb.like(cb.lower(wbs.get("wbsName")), searchString),
						cb.like(cb.lower(project.get("projectCode")), searchString),
						cb.like(cb.lower(project.get("projectName")), searchString),
						cb.like(cb.lower(currency.get("masterCurrencyCode")), searchString),
						cb.like(cb.lower(currency.get("masterCurrencyName")), searchString));
			};
		}

		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(), pageRequestDto.getPageSize(),
				Sort.by(Sort.Direction.DESC, pageRequestDto.getSort()));
		Page<BudgetEntity> budgets = search
				? budgetRepository.findAll(Specification.anyOf(spec), pageable)
				: budgetRepository.findAll(pageable);
		return new PageImpl<>(budgetMapper.toDtoList(budgets.getContent()),
				budgets.getPageable(), budgets.getTotalElements());
	}

	private final DeliveryLocationRepository deliveryLocationRepository;
	private final DeliveryLocationMapper deliveryLocationMapper;

	public DeliveryLocationResponseDto addDeliveryLocation(DeliveryLocationCreateRequestDto request) {
		String locationName = request.getLocationName().trim();
		if (deliveryLocationRepository.existsByLocationNameIgnoreCaseAndBusinessUnit_Id(
				locationName, request.getBusinessUnitId()))
			throw new DuplicateResourceException("Delivery Location Already Exists In This Business Unit");
		BusinessUnit businessUnit = businessUnitRepository.findById(request.getBusinessUnitId())
				.orElseThrow(() -> new NotFoundResourceException("Business Unit Not Found"));
		DeliveryLocationEntity deliveryLocation = DeliveryLocationEntity.builder()
				.deliveryLocationCode(getMasterEntityCode(MasterEntityTypeEnum.DELIVERY_LOCATION))
				.locationName(locationName)
				.addressLine1(request.getAddressLine1().trim())
				.addressLine2(request.getAddressLine2())
				.city(request.getCity().trim())
				.state(request.getState().trim())
				.country(request.getCountry().trim())
				.postalCode(request.getPostalCode().trim())
				.businessUnit(businessUnit)
				.contactPersonName(request.getContactPersonName())
				.contactPersonEmail(request.getContactPersonEmail())
				.contactPersonNumber(request.getContactPersonNumber())
				.status(CommonStatusEnum.ACTIVE)
				.build();
		return deliveryLocationMapper.toDto(deliveryLocationRepository.save(deliveryLocation));
	}

	public DeliveryLocationResponseDto updateDeliveryLocation(String id, DeliveryLocationUpdateRequestDto request) {
		DeliveryLocationEntity deliveryLocation = deliveryLocationRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("Delivery Location Not Found"));
		BusinessUnit businessUnit = deliveryLocation.getBusinessUnit();
		if (request.getBusinessUnitId() != null && !request.getBusinessUnitId().isBlank()) {
			businessUnit = businessUnitRepository.findById(request.getBusinessUnitId())
					.orElseThrow(() -> new NotFoundResourceException("Business Unit Not Found"));
		}
		String locationName = request.getLocationName();
		if (locationName != null && !locationName.isBlank())
			locationName = locationName.trim();
		else
			locationName = deliveryLocation.getLocationName();
		if (deliveryLocationRepository.existsByLocationNameIgnoreCaseAndBusinessUnit_IdAndIdNot(
				locationName, businessUnit.getId(), id))
			throw new DuplicateResourceException("Delivery Location Already Exists In This Business Unit");

		deliveryLocation.setLocationName(locationName);
		if (request.getAddressLine1() != null && !request.getAddressLine1().isBlank())
			deliveryLocation.setAddressLine1(request.getAddressLine1().trim());
		if (request.getAddressLine2() != null)
			deliveryLocation.setAddressLine2(request.getAddressLine2());
		if (request.getCity() != null && !request.getCity().isBlank())
			deliveryLocation.setCity(request.getCity().trim());
		if (request.getState() != null && !request.getState().isBlank())
			deliveryLocation.setState(request.getState().trim());
		if (request.getCountry() != null && !request.getCountry().isBlank())
			deliveryLocation.setCountry(request.getCountry().trim());
		if (request.getPostalCode() != null && !request.getPostalCode().isBlank())
			deliveryLocation.setPostalCode(request.getPostalCode().trim());
		deliveryLocation.setBusinessUnit(businessUnit);
		if (request.getContactPersonName() != null)
			deliveryLocation.setContactPersonName(request.getContactPersonName());
		if (request.getContactPersonEmail() != null)
			deliveryLocation.setContactPersonEmail(request.getContactPersonEmail());
		if (request.getContactPersonNumber() != null)
			deliveryLocation.setContactPersonNumber(request.getContactPersonNumber());
		if (request.getStatus() != null)
			deliveryLocation.setStatus(request.getStatus());
		return deliveryLocationMapper.toDto(deliveryLocationRepository.save(deliveryLocation));
	}

	public void deleteDeliveryLocation(String id) {
		DeliveryLocationEntity deliveryLocation = deliveryLocationRepository.findById(id)
				.orElseThrow(() -> new NotFoundResourceException("Delivery Location Not Found"));
		deliveryLocation.setStatus(CommonStatusEnum.INACTIVE);
		deliveryLocationRepository.save(deliveryLocation);
	}

	public Page<DeliveryLocationResponseDto> getDeliveryLocationList(PageRequestDto pageRequestDto) {
		if (pageRequestDto == null)
			pageRequestDto = new PageRequestDto();
		if (pageRequestDto.getSort() == null || pageRequestDto.getSort().isBlank())
			pageRequestDto.setSort("createdDate");

		Specification<DeliveryLocationEntity> spec = null;
		boolean search = pageRequestDto.getSearch() != null && !pageRequestDto.getSearch().isBlank();
		if (search) {
			String searchString = "%" + pageRequestDto.getSearch().trim().toLowerCase() + "%";
			spec = (root, query, cb) -> {
				Join<DeliveryLocationEntity, BusinessUnit> businessUnit = root.join("businessUnit", JoinType.LEFT);
				return cb.or(
						cb.like(cb.lower(root.get("deliveryLocationCode")), searchString),
						cb.like(cb.lower(root.get("locationName")), searchString),
						cb.like(cb.lower(root.get("addressLine1")), searchString),
						cb.like(cb.lower(root.get("addressLine2")), searchString),
						cb.like(cb.lower(root.get("city")), searchString),
						cb.like(cb.lower(root.get("state")), searchString),
						cb.like(cb.lower(root.get("country")), searchString),
						cb.like(cb.lower(root.get("postalCode")), searchString),
						cb.like(cb.lower(root.get("contactPersonName")), searchString),
						cb.like(cb.lower(root.get("contactPersonEmail")), searchString),
						cb.like(cb.lower(root.get("contactPersonNumber")), searchString),
						cb.like(cb.lower(businessUnit.get("businessUnitCode")), searchString),
						cb.like(cb.lower(businessUnit.get("businessUnitName")), searchString));
			};
		}

		Pageable pageable = PageRequest.of(pageRequestDto.getPageNumber(), pageRequestDto.getPageSize(),
				Sort.by(Sort.Direction.DESC, pageRequestDto.getSort()));
		Page<DeliveryLocationEntity> locations = search
				? deliveryLocationRepository.findAll(Specification.anyOf(spec), pageable)
				: deliveryLocationRepository.findAll(pageable);
		return new PageImpl<>(deliveryLocationMapper.toDtoList(locations.getContent()),
				locations.getPageable(), locations.getTotalElements());
	}

}
