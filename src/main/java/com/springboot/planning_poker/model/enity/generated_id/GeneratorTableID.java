package com.springboot.planning_poker.model.enity.generated_id;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Base64;

public class GeneratorTableID implements IdentifierGenerator{

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return Base64.getEncoder().withoutPadding().encodeToString( String.valueOf(Math.random()).getBytes());
    }
}