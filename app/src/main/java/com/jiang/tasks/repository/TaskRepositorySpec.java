package com.jiang.tasks.repository;

import com.jiang.tasks.domain.Task;
import com.jiang.tasks.dto.TaskQueryDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TaskRepositorySpec {
    public static Specification<Task> getSpec(TaskQueryDto taskQueryDto) {
        return new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p1 = null;
                if (taskQueryDto.getTitle() != null) {
                    Predicate p2 = cb.equal(root.get("title"), taskQueryDto.getTitle());
                    p1 = addCriteria(cb, p1, p2);
                }
                if (taskQueryDto.getFrom() != null) {
                    Predicate p2 = cb.greaterThanOrEqualTo(root.get("dueDate"), taskQueryDto.getFrom());
                    p1 = addCriteria(cb, p1, p2);

                }
                if (taskQueryDto.getTo() != null) {
                    Predicate p2 = cb.lessThanOrEqualTo(root.get("dueDate"), taskQueryDto.getTo());
                    p1 = addCriteria(cb, p1, p2);
                }
                if (taskQueryDto.getStatus() != null) {
                    Predicate p2 = cb.equal(root.get("title"), taskQueryDto.getStatus());
                    p1 = addCriteria(cb, p1, p2);
                }
                return p1;
            }

            private Predicate addCriteria(CriteriaBuilder cb, Predicate p1, Predicate p2) {
                if (p1 != null) {
                    return cb.and(p1, p2);
                } else {
                    return p2;
                }
            }
        };
    }
}
