package com.fionapet.business.repository;

import com.fionapet.business.entity.ItemCount;
import com.fionapet.business.entity.OrderVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.entity.SearchFilter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 种类数量更改原因表
* Created by tom on 2016-07-25 09:32:32.
 */
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml","/META-INF/spring/fionapet-business-service-provider.xml"})
public class ItemCountDaoTest extends SpringTransactionalTestCase {
    private final static String WHERE_TYPE_AND = "AND";
    private final static String WHERE_TYPE_OR = "OR";

    @Autowired
    private ItemCountDao itemCountDao;

    @Test
    public void order(){
        List<OrderVO> itemCounts = itemCountDao.genOrder();

        Assert.assertNotNull(itemCounts);

        Assert.assertEquals(itemCounts.size(), 2);
    }

    @Test
    public void findAll(){
        List<ItemCount> itemCounts = itemCountDao.findAllBy();

        Assert.assertNotNull(itemCounts);

        Assert.assertEquals(itemCounts.size(), 0);
    }
    @Test
    public void search(){
        PageSearch pageSearch = new PageSearch();
        Sort sort = new Sort(Sort.Direction.fromString("desc"), "createDate");

        Pageable pageable = new PageRequest(pageSearch.getPageNumber() - 1, pageSearch.getPageSize(), sort);

        List<SearchFilter> orFilter = new ArrayList<SearchFilter>();
        SearchFilter searchFilter = new SearchFilter("itemName", SearchFilter.Operator.LIKE, "大");
        orFilter.add(searchFilter);

        List<SearchFilter> andFilter = new ArrayList<SearchFilter>();
        searchFilter = new SearchFilter("warehouseId", SearchFilter.Operator.EQ, "5574bbac-9824-11e6-adb2-4ccc6a1bc949");
        andFilter.add(searchFilter);

        pageSearch.setFilters(orFilter);
        pageSearch.setAndFilters(andFilter);


        Specification<ItemCount> spec = bySearchFilter(pageSearch.getFilters(), pageSearch.getAndFilters());

        Page<ItemCount> page = itemCountDao.findAll(spec, pageable);

        System.out.println(page.getTotalElements());
    }

    public Specification<ItemCount> bySearchFilter(final List<SearchFilter> filters) {
        return new Specification<ItemCount>() {
            @Override
            public Predicate toPredicate(Root<ItemCount> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (CollectionUtils.isNotEmpty(filters)) {

                    List<Predicate> predicates = Lists.newArrayList();
                    for (SearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // logic operator
                        switch (filter.operator) {
                            case EQ:
                                predicates.add(builder.equal(expression, filter.value));
                                break;
                            case LIKE:
                                predicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case GT:
                                predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case LT:
                                predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case GTE:
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case LTE:
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case NOT_EQ:
                                predicates.add(builder.notEqual(expression, (Comparable) filter.value));
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.or(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }

    public Specification<ItemCount> bySearchFilter(final List<SearchFilter> filters, final List<SearchFilter> andFilters) {
        return new Specification<ItemCount>() {
            @Override
            public Predicate toPredicate(Root<ItemCount> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate orFilter = toPredicates(root, builder, filters, WHERE_TYPE_OR);
                Predicate andFilter = toPredicates(root, builder, andFilters, WHERE_TYPE_AND);

                System.out.println(orFilter.getAlias());
                System.out.println(andFilter);

                return builder.and(orFilter, andFilter);

            }
        };
    }

    private Predicate toPredicates(Root<ItemCount> root, CriteriaBuilder builder,List<SearchFilter> filters, String whereType){
        List<Predicate> predicates = Lists.newArrayList();

        for (SearchFilter filter : filters) {
            // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
            String[] names = StringUtils.split(filter.fieldName, ".");
            Path expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }

            // logic operator
            switch (filter.operator) {
                case EQ:
                    predicates.add(builder.equal(expression, filter.value));
                    break;
                case LIKE:
                    predicates.add(builder.like(expression, "%" + filter.value + "%"));
                    break;
                case GT:
                    predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                    break;
                case LT:
                    predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                    break;
                case GTE:
                    predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                    break;
                case LTE:
                    predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                    break;
                case NOT_EQ:
                    predicates.add(builder.notEqual(expression, (Comparable) filter.value));
                    break;
            }
        }
        // 将所有条件用 and 联合起来
        if (!predicates.isEmpty()) {
            if (WHERE_TYPE_AND.equalsIgnoreCase(whereType)) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }else{
                return builder.or(predicates.toArray(new Predicate[predicates.size()]));
            }
        }

        return null;
    }

}
