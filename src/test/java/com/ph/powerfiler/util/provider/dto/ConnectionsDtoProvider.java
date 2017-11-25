package com.ph.powerfiler.util.provider.dto;

import com.ph.powerfiler.model.dto.ConnectionsDto;
import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;

import java.util.ArrayList;
import java.util.List;

public class ConnectionsDtoProvider {

    public ConnectionsDto createConnectionsDtoWithNullValues(){

        return new ConnectionsDto(null, null);

    }
    public ConnectionsDto createConnectionsDtoWithEmptyMeterDtosAndNullFractionDtos(){

        return new ConnectionsDto(new ArrayList<>(), null);

    }
    public ConnectionsDto createConnectionsDtoWithMeterDtosAndNullFractionDtos(){
        MeterDtoProvider meterDtoProivder = new MeterDtoProvider();
        List<MeterDto> meterDtos = meterDtoProivder.createMeterDtos();
        return new ConnectionsDto(meterDtos, null);

    }
    public ConnectionsDto createConnectionsDtoWithMeterDtosAndFractionDtos(){
        MeterDtoProvider meterDtoProivder = new MeterDtoProvider();
        List<MeterDto> meterDtos = meterDtoProivder.createMeterDtos();
        FractionDtoProvider fractionDtoProvider = new FractionDtoProvider();
        List<FractionDto> fractionDtos = fractionDtoProvider.createFractionDtos();
        return new ConnectionsDto(meterDtos, fractionDtos);

    }


}
