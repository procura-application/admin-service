package com.eprocurement.admin.masters.controller;

import com.eprocurement.admin.common.dto.PageRequestDto;
import com.eprocurement.admin.masters.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eprocurement.admin.common.dto.ApiResponse;
import com.eprocurement.admin.masters.service.MasterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/masters/")
@RequiredArgsConstructor
public class MastersController {
	
	private final MasterService masterService;

		@PostMapping("cost-center")
		public ResponseEntity<ApiResponse<CostCenterResponseDto>> addCostCenter(@Valid @RequestBody CostCenterRequestDto request) {
			CostCenterResponseDto response = masterService.addCostCenter(request);
			return ResponseEntity.ok(ApiResponse.<CostCenterResponseDto>builder()
					.data(response).message("Cost Center Added Successfully").errorList(null).build());
		}

		@PatchMapping("cost-center/{id}")
		public ResponseEntity<ApiResponse<CostCenterResponseDto>> updateCostCenter(@Valid @RequestBody CostCenterRequestDto request,
				@PathVariable String id) {
			CostCenterResponseDto response = masterService.updateCostCenter(id, request);
			return ResponseEntity.ok(ApiResponse.<CostCenterResponseDto>builder()
					.data(response).message("Cost Center Updated Successfully").errorList(null).build());
		}

		@DeleteMapping("cost-center/{id}")
		public ResponseEntity<ApiResponse<Void>> deleteCostCenter(@PathVariable String id) {
			masterService.deleteCostCenter(id);
			return ResponseEntity.ok(ApiResponse.<Void>builder()
					.data(null).message("Cost Center Deleted Successfully").errorList(null).build());
		}

		@GetMapping("cost-center")
		public ResponseEntity<ApiResponse<Page<CostCenterResponseDto>>> getCostCenterList(@Valid PageRequestDto pageRequestDto) {
			Page<CostCenterResponseDto> response = masterService.getCostCenterList(pageRequestDto);
			return ResponseEntity.ok(ApiResponse.<Page<CostCenterResponseDto>>builder()
					.data(response).message("Cost Center List Retrieved Successfully").errorList(null).build());
		}
	
	@PostMapping("businessUnit")
	public ResponseEntity<ApiResponse<BusinessUnitResponseDto>> addBusinessUnit(@Valid @RequestBody BusinessUnitRequestDto businessUnitRequestDto){
		
			BusinessUnitResponseDto businessUnitResponseDto = masterService.addBusinessUnit(businessUnitRequestDto);
		return ResponseEntity.ok(ApiResponse.<BusinessUnitResponseDto>builder()
				.data(businessUnitResponseDto)
				.message("Business Unit Added Successfully")
				.errorList(null).build()
				);
				
	}

	@PutMapping("businessUnit/{id}")
	public ResponseEntity<ApiResponse<BusinessUnitResponseDto>> updateBusinessUnit(@Valid @RequestBody BusinessUnitRequestDto businessUnitRequestDto,@PathVariable String id){

		BusinessUnitResponseDto businessUnitResponseDto = masterService.updateBusinessUnit(businessUnitRequestDto,id);
		return ResponseEntity.ok(ApiResponse.<BusinessUnitResponseDto>builder()
				.data(businessUnitResponseDto)
				.message("Business Unit Updated Successfully")
				.errorList(null).build()
		);

	}

	@DeleteMapping("businessUnit/{id}")
	public ResponseEntity<ApiResponse<String>> deleteBusinessUnit(@PathVariable String id){

		masterService.deleteBusinessUnit(id);
		return ResponseEntity.ok(ApiResponse.<String>builder()
				.data(null)
				.message("Business Unit Deleted Successfully")
				.errorList(null).build()
		);

	}

	@GetMapping("businessUnit")
	public ResponseEntity<ApiResponse<Page<BusinessUnitResponseDto>>> getBusinessUnitList(@Valid  PageRequestDto pageRequestDto){

		Page<BusinessUnitResponseDto> businessUnitResponseDtos =  masterService.getBusinessUnitList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<BusinessUnitResponseDto>>builder()
				.data(businessUnitResponseDtos)
				.message("Business Unit Retrieved Successfully")
				.errorList(null).build()
		);

	}


	@PostMapping("category")
	public ResponseEntity<ApiResponse<CategoryResponseDto>> addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto){

		CategoryResponseDto categoryResponseDto = masterService.addCategory(categoryRequestDto);
		return ResponseEntity.ok(ApiResponse.<CategoryResponseDto>builder()
				.data(categoryResponseDto)
				.message("Category Added Successfully")
				.errorList(null).build()
		);

	}

	@PatchMapping("category/{id}")
	public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto,@PathVariable String id){

		CategoryResponseDto categoryResponseDto = masterService.updateCategory(categoryRequestDto,id);
		return ResponseEntity.ok(ApiResponse.<CategoryResponseDto>builder()
				.data(categoryResponseDto)
				.message("Category Updated Successfully")
				.errorList(null).build()
		);

	}

	@DeleteMapping("category/{id}")
	public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable String id){

		masterService.deleteCategory(id);
		return ResponseEntity.ok(ApiResponse.<String>builder()
				.data(null)
				.message("Category Deleted Successfully")
				.errorList(null).build()
		);

	}

	@GetMapping("category")
	public ResponseEntity<ApiResponse<Page<CategoryResponseDto>>> getCategoryList(@Valid  PageRequestDto pageRequestDto){

		Page<CategoryResponseDto> categoryResponseDtos =  masterService.getMasterCategoryList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<CategoryResponseDto>>builder()
				.data(categoryResponseDtos)
				.message("Category List Retrieved Successfully")
				.errorList(null).build()
		);

	}

	@PostMapping("/master-item")
	public ResponseEntity<ApiResponse<MasterItemResponseDto>> addMasterItem(@RequestBody MasterItemRequestDto masterItemRequestDto){

		MasterItemResponseDto masterItemResponseDto = masterService.addMasterItem(masterItemRequestDto);
		return ResponseEntity.ok(ApiResponse.<MasterItemResponseDto>builder()
				.data(masterItemResponseDto)
				.message("Master Item Added Successfully")
				.errorList(null).build()
		);
	}

	@PatchMapping("/master-item/{masterItemId}")
	public ResponseEntity<ApiResponse<MasterItemResponseDto>> updateMasterItem(@RequestBody MasterItemRequestDto masterItemRequestDto,@PathVariable String masterItemId){

		MasterItemResponseDto masterItemResponseDto = masterService.updateMasterItem(masterItemId,masterItemRequestDto);
		return ResponseEntity.ok(ApiResponse.<MasterItemResponseDto>builder()
				.data(masterItemResponseDto)
				.message("Master item Updated Successfully")
				.errorList(null).build()
		);
	}

	@DeleteMapping("/master-item/{masterItemId}")
	public ResponseEntity<ApiResponse<Void>> deleteMasterItem(@PathVariable String masterItemId){
		masterService.deleteMasterItem(masterItemId);
		return ResponseEntity.ok(ApiResponse.<Void>builder()
				.data(null)
				.message("Master item Deleted Successfully")
				.errorList(null).build()
		);
	}

	@GetMapping("/master-item")
	public ResponseEntity<ApiResponse<Page<MasterItemResponseDto>>> getMasterItemList(@RequestBody(required = false) PageRequestDto pageRequestDto){
		if(pageRequestDto==null) pageRequestDto = new PageRequestDto();
		Page<MasterItemResponseDto> masterItemResponseDtos = masterService.getMasterItemList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<MasterItemResponseDto>>builder()
				.data(masterItemResponseDtos)
				.message("Master item List Fetched Successfully")
				.errorList(null).build()
		);
	}

	@PostMapping("/master-currency")
	public ResponseEntity<ApiResponse<MasterCurrencyResponseDto>> addMasterCurrency(@RequestBody @Valid MasterCurrencyAddRequestDto masterCurrencyAddRequestDto){
		MasterCurrencyResponseDto masterCurrencyResponseDto = masterService.addCurrency(masterCurrencyAddRequestDto);
		return ResponseEntity.ok(ApiResponse.<MasterCurrencyResponseDto>builder()
				.data(masterCurrencyResponseDto)
				.message("Master currency Added Successfully")
				.errorList(null).build()
		);
	}

	@PatchMapping("/master-currency")
	public ResponseEntity<ApiResponse<MasterCurrencyResponseDto>> updateMasterCurrency(@RequestBody @Valid MasterCurrencyUpdateRequestDto masterCurrencyUpdateRequestDto){

		MasterCurrencyResponseDto masterCurrencyResponseDto = masterService.updateCurrency(masterCurrencyUpdateRequestDto.getMasterCurrencyCode(), masterCurrencyUpdateRequestDto.getMasterCurrencyName());
		return ResponseEntity.ok(ApiResponse.<MasterCurrencyResponseDto>builder()
				.data(masterCurrencyResponseDto)
				.message("Master currency Updated Successfully")
				.errorList(null).build()
		);
	}

	@DeleteMapping("/master-currency/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteMasterCurrency(@PathVariable String id){
		masterService.deleteCurrency(id);
		return ResponseEntity.ok(ApiResponse.<Void>builder()
				.data(null)
				.message("Master currency Deleted Successfully")
				.errorList(null).build()
		);
	}

	@GetMapping("/master-currency")
	public ResponseEntity<ApiResponse<Page<MasterCurrencyResponseDto>>> getMasterCurrencyList(@RequestBody(required = false) PageRequestDto pageRequestDto){
		if(pageRequestDto==null) pageRequestDto = new PageRequestDto();
		Page<MasterCurrencyResponseDto> masterCurrencyResponseDtoPage = masterService.getMasterCurrencyList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<MasterCurrencyResponseDto>>builder()
				.data(masterCurrencyResponseDtoPage)
				.message("Master currency list retrieved Successfully")
				.errorList(null).build()
		);
	}

	@PostMapping("/master-unit")
	public ResponseEntity<ApiResponse<MasterUnitResponseDto>> addMasterUnit(@RequestBody @Valid MasterUnitRequestDto masterUnitRequestDto){
		MasterUnitResponseDto masterUnitResponseDto = masterService.addMasterUnit(masterUnitRequestDto);
		return ResponseEntity.ok(ApiResponse.<MasterUnitResponseDto>builder()
				.data(masterUnitResponseDto)
				.message("Master Unit Added Successfully")
				.errorList(null).build()
		);
	}

	@PatchMapping("/master-unit")
	public ResponseEntity<ApiResponse<MasterUnitResponseDto>> updateMasterUnit(@RequestBody @Valid MasterUnitRequestDto masterUnitRequestDto){
		MasterUnitResponseDto masterUnitResponseDto = masterService.updateMasterUnit(masterUnitRequestDto);
		return ResponseEntity.ok(ApiResponse.<MasterUnitResponseDto>builder()
				.data(masterUnitResponseDto)
				.message("Master Unit Updated Successfully")
				.errorList(null).build()
		);
	}

	@DeleteMapping("/master-unit/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteMasterUnit(@PathVariable String id){
	masterService.deleteMasterUnit(id);
		return ResponseEntity.ok(ApiResponse.<Void>builder()
				.data(null)
				.message("Master Unit Deleted Successfully")
				.errorList(null).build()
		);
	}

	@GetMapping("/master-unit")
	public ResponseEntity<ApiResponse<Page<MasterUnitResponseDto>>> getMasterUnitList(@RequestBody(required = false) PageRequestDto pageRequestDto){
		if(pageRequestDto==null) pageRequestDto = new PageRequestDto();
		Page<MasterUnitResponseDto> page = masterService.getMasterUnitList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<MasterUnitResponseDto>>builder()
				.data(page)
				.message("Master Unit List fetched Successfully")
				.errorList(null).build()
		);
	}

	@PostMapping("project")
	public ResponseEntity<ApiResponse<ProjectResponseDto>> addProject(@Valid @RequestBody ProjectCreateRequestDto request) {
		ProjectResponseDto response = masterService.addProject(request);
		return ResponseEntity.ok(ApiResponse.<ProjectResponseDto>builder()
				.data(response).message("Project Added Successfully").errorList(null).build());
	}

	@PatchMapping("project/{id}")
	public ResponseEntity<ApiResponse<ProjectResponseDto>> updateProject(@Valid @RequestBody ProjectUpdateRequestDto request,
			@PathVariable String id) {
		ProjectResponseDto response = masterService.updateProject(id, request);
		return ResponseEntity.ok(ApiResponse.<ProjectResponseDto>builder()
				.data(response).message("Project Updated Successfully").errorList(null).build());
	}

	@DeleteMapping("project/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable String id) {
		masterService.deleteProject(id);
		return ResponseEntity.ok(ApiResponse.<Void>builder()
				.data(null).message("Project Deleted Successfully").errorList(null).build());
	}

	@GetMapping("project")
	public ResponseEntity<ApiResponse<Page<ProjectResponseDto>>> getProjectList(@Valid PageRequestDto pageRequestDto) {
		Page<ProjectResponseDto> response = masterService.getProjectList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<ProjectResponseDto>>builder()
				.data(response).message("Project List Retrieved Successfully").errorList(null).build());
	}

	@PostMapping("wbs")
	public ResponseEntity<ApiResponse<WbsResponseDto>> addWbs(@Valid @RequestBody WbsCreateRequestDto request) {
		WbsResponseDto response = masterService.addWbs(request);
		return ResponseEntity.ok(ApiResponse.<WbsResponseDto>builder()
				.data(response).message("WBS Added Successfully").errorList(null).build());
	}

	@PatchMapping("wbs/{id}")
	public ResponseEntity<ApiResponse<WbsResponseDto>> updateWbs(@Valid @RequestBody WbsUpdateRequestDto request,
			@PathVariable String id) {
		WbsResponseDto response = masterService.updateWbs(id, request);
		return ResponseEntity.ok(ApiResponse.<WbsResponseDto>builder()
				.data(response).message("WBS Updated Successfully").errorList(null).build());
	}

	@DeleteMapping("wbs/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteWbs(@PathVariable String id) {
		masterService.deleteWbs(id);
		return ResponseEntity.ok(ApiResponse.<Void>builder()
				.data(null).message("WBS Deleted Successfully").errorList(null).build());
	}

	@GetMapping("wbs")
	public ResponseEntity<ApiResponse<Page<WbsResponseDto>>> getWbsList(@Valid PageRequestDto pageRequestDto) {
		Page<WbsResponseDto> response = masterService.getWbsList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<WbsResponseDto>>builder()
				.data(response).message("WBS List Retrieved Successfully").errorList(null).build());
	}

	@PostMapping("budget")
	public ResponseEntity<ApiResponse<BudgetResponseDto>> addBudget(@Valid @RequestBody BudgetCreateRequestDto request) {
		BudgetResponseDto response = masterService.addBudget(request);
		return ResponseEntity.ok(ApiResponse.<BudgetResponseDto>builder()
				.data(response).message("Budget Added Successfully").errorList(null).build());
	}

	@PatchMapping("budget/{id}")
	public ResponseEntity<ApiResponse<BudgetResponseDto>> updateBudget(@Valid @RequestBody BudgetUpdateRequestDto request,
			@PathVariable String id) {
		BudgetResponseDto response = masterService.updateBudget(id, request);
		return ResponseEntity.ok(ApiResponse.<BudgetResponseDto>builder()
				.data(response).message("Budget Updated Successfully").errorList(null).build());
	}

	@DeleteMapping("budget/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteBudget(@PathVariable String id) {
		masterService.deleteBudget(id);
		return ResponseEntity.ok(ApiResponse.<Void>builder()
				.data(null).message("Budget Deleted Successfully").errorList(null).build());
	}

	@GetMapping("budget")
	public ResponseEntity<ApiResponse<Page<BudgetResponseDto>>> getBudgetList(@Valid PageRequestDto pageRequestDto) {
		Page<BudgetResponseDto> response = masterService.getBudgetList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<BudgetResponseDto>>builder()
				.data(response).message("Budget List Retrieved Successfully").errorList(null).build());
	}

	@PostMapping("delivery-location")
	public ResponseEntity<ApiResponse<DeliveryLocationResponseDto>> addDeliveryLocation(
			@Valid @RequestBody DeliveryLocationCreateRequestDto request) {
		DeliveryLocationResponseDto response = masterService.addDeliveryLocation(request);
		return ResponseEntity.ok(ApiResponse.<DeliveryLocationResponseDto>builder()
				.data(response).message("Delivery Location Added Successfully").errorList(null).build());
	}

	@PatchMapping("delivery-location/{id}")
	public ResponseEntity<ApiResponse<DeliveryLocationResponseDto>> updateDeliveryLocation(
			@Valid @RequestBody DeliveryLocationUpdateRequestDto request, @PathVariable String id) {
		DeliveryLocationResponseDto response = masterService.updateDeliveryLocation(id, request);
		return ResponseEntity.ok(ApiResponse.<DeliveryLocationResponseDto>builder()
				.data(response).message("Delivery Location Updated Successfully").errorList(null).build());
	}

	@DeleteMapping("delivery-location/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteDeliveryLocation(@PathVariable String id) {
		masterService.deleteDeliveryLocation(id);
		return ResponseEntity.ok(ApiResponse.<Void>builder()
				.data(null).message("Delivery Location Deleted Successfully").errorList(null).build());
	}

	@GetMapping("delivery-location")
	public ResponseEntity<ApiResponse<Page<DeliveryLocationResponseDto>>> getDeliveryLocationList(
			@Valid PageRequestDto pageRequestDto) {
		Page<DeliveryLocationResponseDto> response = masterService.getDeliveryLocationList(pageRequestDto);
		return ResponseEntity.ok(ApiResponse.<Page<DeliveryLocationResponseDto>>builder()
				.data(response).message("Delivery Location List Retrieved Successfully").errorList(null).build());
	}


}
