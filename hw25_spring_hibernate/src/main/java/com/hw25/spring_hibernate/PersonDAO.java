package com.hw25.spring_hibernate;

import java.util.List;

    public interface PersonDAO {

        public void save(Person p);

        public List<Person> list();

    }

