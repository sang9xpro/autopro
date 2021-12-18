package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.CommentsFields;
import com.sang9xpro.autopro.repository.CommentsFieldsRepository;
import com.sang9xpro.autopro.service.CommentsFieldsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommentsFields}.
 */
@Service
@Transactional
public class CommentsFieldsServiceImpl implements CommentsFieldsService {

    private final Logger log = LoggerFactory.getLogger(CommentsFieldsServiceImpl.class);

    private final CommentsFieldsRepository commentsFieldsRepository;

    public CommentsFieldsServiceImpl(CommentsFieldsRepository commentsFieldsRepository) {
        this.commentsFieldsRepository = commentsFieldsRepository;
    }

    @Override
    public CommentsFields save(CommentsFields commentsFields) {
        log.debug("Request to save CommentsFields : {}", commentsFields);
        return commentsFieldsRepository.save(commentsFields);
    }

    @Override
    public Optional<CommentsFields> partialUpdate(CommentsFields commentsFields) {
        log.debug("Request to partially update CommentsFields : {}", commentsFields);

        return commentsFieldsRepository
            .findById(commentsFields.getId())
            .map(existingCommentsFields -> {
                if (commentsFields.getFieldName() != null) {
                    existingCommentsFields.setFieldName(commentsFields.getFieldName());
                }

                return existingCommentsFields;
            })
            .map(commentsFieldsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentsFields> findAll() {
        log.debug("Request to get all CommentsFields");
        return commentsFieldsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentsFields> findOne(Long id) {
        log.debug("Request to get CommentsFields : {}", id);
        return commentsFieldsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommentsFields : {}", id);
        commentsFieldsRepository.deleteById(id);
    }
}
