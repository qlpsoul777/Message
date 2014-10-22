package utils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import play.db.jpa.JPA;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by qlp on 2014/10/22.
 */
public class FindUtil {
    private final Session session = (Session)JPA.em().getDelegate();
    private Class<?> entityClass;

    public FindUtil( Class<?> entityClass){
        this.entityClass = entityClass;
    }

    public List<?> queryByMap(Map<String,Object> map){
        return mapToCriteria(map).list();
    }

    public Criteria mapToCriteria(Map<String, Object> map) {
        Criteria c = session.createCriteria(this.entityClass);
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            Object object = entry.getValue();
            if ((object != null) && (StringUtils.isNotEmpty(object.toString()))) {
                Criterion criterion = entryToCriterion((String) entry.getKey(), entry.getValue());
                c.add(criterion);
            }
        }
        return c;
    }

    private Criterion entryToCriterion(String key, Object o) {
        String[] k = StringUtils.split(key, '_');
        if (k.length < 2) {
            return Restrictions.eq(k[0], o); //无后缀
        }
        if (StringUtils.equals("eq", k[1])) {
            return Restrictions.eq(k[0], o); //相等
        }
        if (StringUtils.equals("ne", k[1])) {
            return Restrictions.ne(k[0], o); //不相等
        }
        if (StringUtils.equals("lt", k[1])) {
            return Restrictions.lt(k[0], o); //小于
        }
        if (StringUtils.equals("le", k[1])) {
            return Restrictions.le(k[0], o); //小于等于
        }
        if (StringUtils.equals("gt", k[1])) {
            return Restrictions.gt(k[0], o); //大于
        }
        if (StringUtils.equals("ge", k[1])) {
            return Restrictions.ge(k[0], o); //大于等于
        }
        if (StringUtils.equals("li", k[1])) {
            return Restrictions.like(k[0], "%" + o + "%"); //like
        }
        if (StringUtils.equals("nl", k[1])) {
            return Restrictions.not(Restrictions.like(k[0], "%" + o + "%")); //not like
        }
        if (StringUtils.equals("in", k[1])) {
            return Restrictions.in(k[0], (List) o); //in
        }
        if (StringUtils.equals("ni", k[1])) {
            return Restrictions.not(Restrictions.in(k[0], (List) o)); //not in
        }
        return Restrictions.eq(key, o);
    }

}
