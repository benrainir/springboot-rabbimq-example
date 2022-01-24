
package com.globoplay.consumer.repository;

import com.globoplay.consumer.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findById(long id);
}