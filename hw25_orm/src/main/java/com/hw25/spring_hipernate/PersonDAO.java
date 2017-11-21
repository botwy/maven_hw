package com.hw25.spring_hipernate;

import java.util.List;

    public interface PersonDAO {

        public void save(Person p);

        public List<Person> list();

    }

