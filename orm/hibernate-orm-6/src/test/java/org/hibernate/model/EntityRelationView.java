package org.hibernate.model;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static org.hibernate.model.EntityRelationView.TABLE_NAME;

@Entity
@Immutable
@Table(name = TABLE_NAME)
public class EntityRelationView {

    public static final String TABLE_NAME_NO_QUOTES = "com.proj.db.views::entity_relation";

    public static final String TABLE_NAME = "`" + TABLE_NAME_NO_QUOTES + "`";

    @Column(name = "IF_NODE", nullable = false, length = 5000, updatable = false, insertable = false)
    private Integer queryNode;

    @Id
    @Column(name = "RESULT_NODE", nullable = false, length = 5000, updatable = false, insertable = false)
    private Integer resultNode;

    public EntityRelationView() {
    }

    public EntityRelationView(Integer resultNode) {
        this.resultNode = resultNode;
    }

    public Integer getQueryNode() {
        return queryNode;
    }

    public void setQueryNode(Integer queryNode) {
        this.queryNode = queryNode;
    }

    public Integer getResultNode() {
        return resultNode;
    }

    public void setResultNode(Integer resultNode) {
        this.resultNode = resultNode;
    }

}
