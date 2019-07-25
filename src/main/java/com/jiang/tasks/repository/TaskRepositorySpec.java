package com.jiang.tasks.repository;

import com.jiang.tasks.domain.Task;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class TaskRepositorySpec {
    public static Specification<Task> getSpec(Map<String, String> requestParams) {
        return new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p1 = null;
                if (requestParams.get("title") != null) {
                    Predicate p2 = cb.equal(root.get("title"),requestParams.get("title"));
                    p1 = addCriteria(cb, p1, p2);
                }
                return p1;
            }

            private Predicate addCriteria(CriteriaBuilder cb, Predicate p1, Predicate p2) {
                if(p1 != null) {
                    return cb.and(p1,p2);
                } else {
                    return p2;
                }
            }
        };
    }
}
