package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.CommentsValues;
import com.sang9xpro.autopro.repository.CommentsValuesRepository;
import com.sang9xpro.autopro.service.CommentsValuesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommentsValues}.
 */
@Service
@Transactional
public class CommentsValuesServiceImpl implements CommentsValuesService {

    private final Logger log = LoggerFactory.getLogger(CommentsValuesServiceImpl.class);

    private final CommentsValuesRepository commentsValuesRepository;

    public CommentsValuesServiceImpl(CommentsValuesRepository commentsValuesRepository) {
        this.commentsValuesRepository = commentsValuesRepository;
    }

    @Override
    public CommentsValues save(CommentsValues commentsValues) {
        log.debug("Request to save CommentsValues : {}", commentsValues);
        return commentsValuesRepository.save(commentsValues);
    }

    @Override
    public Optional<CommentsValues> partialUpdate(CommentsValues commentsValues) {
        log.debug("Request to partially update CommentsValues : {}", commentsValues);

        return commentsValuesRepository
            .findById(commentsValues.getId())
            .map(existingCommentsValues -> {
                if (commentsValues.getValue() != null) {
                    existingCommentsValues.setValue(commentsValues.getValue());
                }

                return existingCommentsValues;
            })
            .map(commentsValuesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentsValues> findAll() {
        log.debug("Request to get all CommentsValues");
        return commentsValuesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentsValues> findOne(Long id) {
        log.debug("Request to get CommentsValues : {}", id);
        return commentsValuesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommentsValues : {}", id);
        commentsValuesRepository.deleteById(id);
    }
}
