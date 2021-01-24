package com.mindex.challenge.controller;

import com.mindex.challenge.data.reportingStructure;
import com.mindex.challenge.service.reportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class reportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(reportingStructureController.class);

    @Autowired
    private reportingStructureService reportingService;

    @GetMapping("/reportingstructure/{id}")
    public reportingStructure read(@PathVariable String id) throws Exception {
        // It will throw an exception when we won't find the employee id in db
        LOG.debug("The reporting structure for Employee with employee id [{}]", id);
        return reportingService.read(id);
    }
}
