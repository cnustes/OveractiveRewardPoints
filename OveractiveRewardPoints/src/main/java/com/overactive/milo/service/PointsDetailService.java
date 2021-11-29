package com.overactive.milo.service;

import java.util.List;

import com.overactive.milo.dto.PointsDetailDTO;

public interface PointsDetailService 
{
	public List<PointsDetailDTO> lDetailDTOs(String customerId);
}
