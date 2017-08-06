package com.example.h.learn.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by h on 2017/7/30.
 */
@Entity(
        // 如果你有超过一个的数据库结构，可以通过这个字段来区分
        // 该实体属于哪个结构
        //schema = "myschema",

        //  实体是否激活的标志，激活的实体有更新，删除和刷新的方法
        active = true,

        // 确定数据库中表的名称
        // 表名称默认是实体类的名称
        nameInDb = "Learn",

        // 索引，其中value值不能写错
        indexes = {
                @Index(value = "name DESC", unique = false)
        },

        // DAO是否应该创建数据库表的标志(默认为true)
        // 如果你有多对一的表，将这个字段设置为false
        // 或者你已经在GreenDAO之外创建了表，也将其置为false
        createInDb = true
)
public class Note {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "NOTENAME")
    private String name;

    @NotNull
    private String noteContent;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 363862535)
    private transient NoteDao myDao;

    @Generated(hash = 252867047)
    public Note(Long id, String name, @NotNull String noteContent) {
        this.id = id;
        this.name = name;
        this.noteContent = noteContent;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoteContent() {
        return this.noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 799086675)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteDao() : null;
    }

}