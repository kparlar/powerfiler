package com.ph.powerfiler.controller.data;


import com.ph.powerfiler.model.entity.Fraction;
import com.ph.powerfiler.model.entity.Meter;
import com.ph.powerfiler.repository.FractionRepository;
import com.ph.powerfiler.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/data/powerfiler/v1/fractions")
public class FractionDataController extends AbstractPowerfilerDataController<Fraction, String> {

    @Autowired
    public FractionDataController(FractionRepository fractionRepository){
        super(fractionRepository);
    }





}
