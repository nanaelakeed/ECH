package com.bfcai.ECH.dao;

import com.bfcai.ECH.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}