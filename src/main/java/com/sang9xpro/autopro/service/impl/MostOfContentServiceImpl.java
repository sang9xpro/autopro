package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.MostOfContent;
import com.sang9xpro.autopro.repository.MostOfContentRepository;
import com.sang9xpro.autopro.service.MostOfContentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MostOfContent}.
 */
@Service
@Transactional
public class MostOfContentServiceImpl implements MostOfContentService {

    private final Logger log = LoggerFactory.getLogger(MostOfContentServiceImpl.class);

    private final MostOfContentRepository mostOfContentRepository;

    public MostOfContentServiceImpl(MostOfContentRepository mostOfContentRepository) {
        this.mostOfContentRepository = mostOfContentRepository;
    }

    @Override
    public MostOfContent save(MostOfContent mostOfContent) {
        log.debug("Request to save MostOfContent : {}", mostOfContent);
        return mostOfContentRepository.save(mostOfContent);
    }

    @Override
    public Optional<MostOfContent> partialUpdate(MostOfContent mostOfContent) {
        log.debug("Request to partially update MostOfContent : {}", mostOfContent);

        return mostOfContentRepository
            .findById(mostOfContent.getId())
            .map(existingMostOfContent -> {
                if (mostOfContent.getUrlOriginal() != null) {
                    existingMostOfContent.setUrlOriginal(mostOfContent.getUrlOriginal());
                }
                if (mostOfContent.getContentText() != null) {
                    existingMostOfContent.setContentText(mostOfContent.getContentText());
                }
                if (mostOfContent.getPostTime() != null) {
                    existingMostOfContent.setPostTime(mostOfContent.getPostTime());
                }
                if (mostOfContent.getNumberLike() != null) {
                    existingMostOfContent.setNumberLike(mostOfContent.getNumberLike());
                }
                if (mostOfContent.getNumberComment() != null) {
                    existingMostOfContent.setNumberComment(mostOfContent.getNumberComment());
                }

                return existingMostOfContent;
            })
            .map(mostOfContentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MostOfContent> findAll() {
        log.debug("Request to get all MostOfContents");
        return mostOfContentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MostOfContent> findOne(Long id) {
        log.debug("Request to get MostOfContent : {}", id);
        return mostOfContentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MostOfContent : {}", id);
        mostOfContentRepository.deleteById(id);
    }
}
