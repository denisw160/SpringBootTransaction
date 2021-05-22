package me.wirries.demo.springtransaction.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * This is a sample model class for this demo.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@Entity
@Table(name = "SAMPLE")
@ToString(callSuper = true)
public class Sample extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "COL_UNIQUE", unique = true, nullable = false)
    private String colUnique;
    @Getter
    @Setter
    @Column(name = "COL_STRING")
    private String colString;
    @Getter
    @Setter
    @Column(name = "COL_INT")
    private Integer colInt;
    @Getter
    @Setter
    @Column(name = "COL_DATE")
    private Date colDate;

}
