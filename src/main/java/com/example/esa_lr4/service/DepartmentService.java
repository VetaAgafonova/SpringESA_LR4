package com.example.esa_lr4.service;

import com.example.esa_lr4.jms.EventType;
import com.example.esa_lr4.jms.Producer;
import com.example.esa_lr4.model.Department;
import com.example.esa_lr4.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final Producer producer;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, Producer producer) {
        this.departmentRepository = departmentRepository;
        this.producer = producer;
    }

    public Department findById(Long id){
        return departmentRepository.getOne(id);
    }

    public List<Department> findAll(){
        return departmentRepository.findAll();
    }

    public Department saveDepartment(Department department){
        return departmentRepository.save(department);
    }

    public void deleteById(Long id){
        departmentRepository.deleteById(id);
        producer.sendMessage(departmentRepository.findOneByUniqueId(id), EventType.delete);
    }
}