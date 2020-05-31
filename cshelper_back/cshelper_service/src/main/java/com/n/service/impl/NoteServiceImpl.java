package com.n.service.impl;

import com.github.pagehelper.PageHelper;
import com.n.dao.INoteDao;
import com.n.domain.BookMark;
import com.n.domain.Note;
import com.n.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class NoteServiceImpl implements INoteService {
    @Autowired
    private INoteDao noteDao;
    @Override
    public void syncNote(String userId) throws Exception {
        List<Note> noteList = noteDao.findAllByUserIdFromMobile(userId);
        for (Note note: noteList) {
            noteDao.saveNote(note);
        }
    }

    @Override
    public List<Note> findAllByUserId(String userId, int page, int size) throws Exception {
        syncNote(userId);
        PageHelper.startPage(page, size);
        return noteDao.findAll(userId);
    }

    @Override
    public Note findById(String id) throws Exception {
        return noteDao.findById(id);
    }
}
