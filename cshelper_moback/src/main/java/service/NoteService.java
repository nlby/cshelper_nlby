package service;

import dao.INoteDao;
import domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class NoteService {
    @Autowired
    private INoteDao noteDao;

    // 新增或更新笔记
    public synchronized void insertOrUpdate(Note note) throws Exception {
        Note note2 = noteDao.findNoteByCreateAt(note.getCreateAt(), note.getUserId()); // 查询某用户在某时间创建的笔记是否存在
        if (note2 != null) {
           note.setId(note2.getId());   // 设置note的id为已存在的笔记的id
           noteDao.updateNote(note);
           return;
        }
        UUID uuid = UUID.randomUUID();
        note.setId(uuid+"");    // 设置note的id为新生成的id
        int count = noteDao.getCount(note.getUserId());
        note.setRid(Integer.toString(++count));
        noteDao.addNote(note);
    }


}
