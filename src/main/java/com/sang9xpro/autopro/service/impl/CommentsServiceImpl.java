package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.Comments;
import com.sang9xpro.autopro.repository.CommentsRepository;
import com.sang9xpro.autopro.service.CommentsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comments}.
 */
@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

    private final Logger log = LoggerFactory.getLogger(CommentsServiceImpl.class);

    private final CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Comments save(Comments comments) {
        log.debug("Request to save Comments : {}", comments);
        return commentsRepository.save(comments);
    }

    @Override
    public Optional<Comments> partialUpdate(Comments comments) {
        log.debug("Request to partially update Comments : {}", comments);

        return commentsRepository
            .findById(comments.getId())
            .map(existingComments -> {
                if (comments.getContent() != null) {
                    existingComments.setContent(comments.getContent());
                }
                if (comments.getImage() != null) {
                    existingComments.setImage(comments.getImage());
                }
                if (comments.getImageContentType() != null) {
                    existingComments.setImageContentType(comments.getImageContentType());
                }
                if (comments.getOwner() != null) {
                    existingComments.setOwner(comments.getOwner());
                }

                return existingComments;
            })
            .map(commentsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comments> findAll() {
        log.debug("Request to get all Comments");
        return commentsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comments> findOne(Long id) {
        log.debug("Request to get Comments : {}", id);
        return commentsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comments : {}", id);
        commentsRepository.deleteById(id);
    }
}
