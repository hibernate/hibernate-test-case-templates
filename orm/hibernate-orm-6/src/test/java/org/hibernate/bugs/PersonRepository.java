package org.hibernate.bugs;

import java.util.List;

import org.hibernate.annotations.processing.SQL;

import jakarta.data.repository.Repository;
import jakarta.data.repository.Save;

@Repository
interface PersonRepository  {
    @SQL("SELECT * FROM Person WHERE name = :name")
    List<Person> findByName(String name);

    @Save
    void save(Person person);
}