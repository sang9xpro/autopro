package com.sang9xpro.autopro.service.impl;

import com.sang9xpro.autopro.domain.Tasks;
import com.sang9xpro.autopro.repository.TasksRepository;
import com.sang9xpro.autopro.service.TasksService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tasks}.
 */
@Service
@Transactional
public class TasksServiceImpl implements TasksService {

    private final Logger log = LoggerFactory.getLogger(TasksServiceImpl.class);

    private final TasksRepository tasksRepository;

    public TasksServiceImpl(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Override
    public Tasks save(Tasks tasks) {
        log.debug("Request to save Tasks : {}", tasks);
        return tasksRepository.save(tasks);
    }

    @Override
    public Optional<Tasks> partialUpdate(Tasks tasks) {
        log.debug("Request to partially update Tasks : {}", tasks);

        return tasksRepository
            .findById(tasks.getId())
            .map(existingTasks -> {
                if (tasks.getTaskName() != null) {
                    existingTasks.setTaskName(tasks.getTaskName());
                }
                if (tasks.getDescription() != null) {
                    existingTasks.setDescription(tasks.getDescription());
                }
                if (tasks.getSource() != null) {
                    existingTasks.setSource(tasks.getSource());
                }
                if (tasks.getPrice() != null) {
                    existingTasks.setPrice(tasks.getPrice());
                }
                if (tasks.getType() != null) {
                    existingTasks.setType(tasks.getType());
                }

                return existingTasks;
            })
            .map(tasksRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tasks> findAll() {
        log.debug("Request to get all Tasks");
        return tasksRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tasks> findOne(Long id) {
        log.debug("Request to get Tasks : {}", id);
        return tasksRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tasks : {}", id);
        tasksRepository.deleteById(id);
    }
}
