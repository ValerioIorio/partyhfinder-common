package com.partyh.finder.common.specifications;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class SpecificationFactory<T, F>{
    public Specification<T> buildSpecification(F filter) {
        Example<T> example = buildExample(filter);
        return convertToSpecification(example, filter);
    }

    private Specification<T> convertToSpecification(Example<T> example, F filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = buildPredicates(root,query,builder,filter);
            if(example!=null){
                predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    protected static ExampleMatcher getExampleMatcherContainingStringAndIgnoreNull() {
        return ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues();
    }
    protected abstract List<Predicate> buildPredicates(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder, F filter) ;


    protected abstract Example<T> buildExample(F filter);
}
