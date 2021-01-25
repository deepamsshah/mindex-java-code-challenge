package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.reportingStructure;
import com.mindex.challenge.service.reportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class reportingStructureServiceImpl implements reportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(reportingStructureServiceImpl.class);

    ArrayDeque<Employee> queue = new ArrayDeque<>();
    int numberOfReports;
    Set<String> visited;

    @Autowired
    private EmployeeRepository employeeRepository;

    private void getNumOfReports(Employee employee) {

        queue = new ArrayDeque<>();
        visited = new HashSet<>();
        queue.add(employee);

        while (!queue.isEmpty()) {

            Employee manager = queue.poll();
            String manager_id = manager.getEmployeeId();

            if (!visited.contains(manager_id)) {

                visited.add(manager_id);
                List<Employee> direct_reports = manager.getDirectReports();

                if(direct_reports!=null){
                    queue.addAll(direct_reports);
                }
                numberOfReports++;
            }
        }
    }

    @Override
    public reportingStructure read(String id) throws RuntimeException {
        numberOfReports = -1;
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Cannot find employeeId: " + id);
        }
        reportingStructure rs = new reportingStructure();
        getNumOfReports(employee);
        rs.setNumberOfReports(numberOfReports);
        rs.setEmployee(employee);
        return rs;
    }
}