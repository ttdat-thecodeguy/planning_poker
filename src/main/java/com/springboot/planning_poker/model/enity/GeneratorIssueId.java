package com.springboot.planning_poker.model.enity;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GeneratorIssueId implements IdentifierGenerator{

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Query<Long> query = session.createQuery("SELECT count(*) FROM Issue", Long.class);
		int count = query.getFirstResult();
		return "PP-" + count;
	}

}
