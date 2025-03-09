package bg.softuni.bookshop.data.entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    protected BaseEntity() {
    }
//    asdgf
//    asfg
    public long getId() {
        return id;
    }
}
