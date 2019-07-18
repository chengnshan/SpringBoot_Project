package com.cxp.springbootelasticsearch.dao;

import com.cxp.springbootelasticsearch.pojo.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 程
 * @date 2019/7/17 下午12:13
 */
@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee,String> {
    /**
     * 查询雇员信息
     * @param id
     * @return
     */
    Employee queryEmployeeById(String id);
}
