package org.hibernate.model;

import jakarta.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = Folder.TABLE_NAME)
@SecondaryTable(name = EntityRelationView.TABLE_NAME, pkJoinColumns = @PrimaryKeyJoinColumn(name = "RESULT_NODE"))
public class Folder {

    public static final String TABLE_NAME_NO_QUOTES = "com.proj.db::base.folder";

    public static final String TABLE_NAME = "`" + TABLE_NAME_NO_QUOTES + "`";

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="increment")
    @GenericGenerator(name="increment", strategy = "org.hibernate.bugs.EntityRelationSequenceStyleGenerator")
    private Integer folderId;

    @Column(name = "NAME", length = 100)
    private String folderName;


    public Folder(Integer folderId, String folderName, Date createdOn, String createdBy, Integer isLeaf, String path) {
        this.folderId = folderId;
        this.folderName = folderName;
    }

    public Folder(String folderName) {
        this.folderName = folderName;
    }

    public Folder(Integer folderId) {
        this.folderId = folderId;
    }

    public Folder() {
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

}
