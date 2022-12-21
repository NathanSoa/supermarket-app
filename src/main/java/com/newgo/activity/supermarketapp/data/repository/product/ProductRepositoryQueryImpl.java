package com.newgo.activity.supermarketapp.data.repository.product;

import com.newgo.activity.supermarketapp.data.entities.Product;
import com.newgo.activity.supermarketapp.data.repository.filter.ProductFilter;

import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

public class ProductRepositoryQueryImpl implements ProductRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Product> findAllFiltered(ProductFilter productFilter){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        Predicate[] predicates = buildFilter(productFilter, builder, root);
        criteria.where(predicates);

        return new HashSet<>(entityManager.createQuery(criteria).getResultList());
    }

    private Predicate[] buildFilter(ProductFilter productFilter, CriteriaBuilder builder, Root<Product> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(descriptionIsNotNull(productFilter))
            predicates.add(
                    builder.like(
                            builder.lower(root.get("description")),
                            "%" + productFilter.getDescription().toLowerCase() + "%"
                    )
            );

        if(nameIsNotNull(productFilter))
            predicates.add(
                    builder.like(
                            builder.lower(root.get("name")),
                            "%" + productFilter.getName().toLowerCase() + "%"
                    )
            );

        if(activeIsNotNull(productFilter))
            predicates.add(
                    builder.equal(root.get("active"), productFilter.getActive())
            );

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private boolean descriptionIsNotNull(ProductFilter productFilter) {
        return !ObjectUtils.isEmpty(productFilter.getDescription());
    }

    private boolean nameIsNotNull(ProductFilter productFilter) {
        return !ObjectUtils.isEmpty(productFilter.getName());
    }

    private boolean activeIsNotNull(ProductFilter productFilter) {
        return !Objects.isNull(productFilter.getActive());
    }
}
