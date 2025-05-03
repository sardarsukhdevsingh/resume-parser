package com.resumeparser.repository;

import com.resumeparser.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
}
