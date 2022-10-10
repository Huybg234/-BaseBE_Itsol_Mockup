package com.itsol.recruit.entity;
import com.itsol.recruit.core.Constants;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name="OTPS")
public class OTPS {
    private final static Long EXPIRED_TIME = Constants.OTP.EXPIRED_TIME;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_OTP_ID")
    @SequenceGenerator(name = "GEN_OTP_ID", sequenceName = "SEQ_OTP", allocationSize = 1)
    private Long id;
    @Column(name="code")
    private Integer code;

    @ManyToOne()
    @JoinColumn(name="user_id")
    User user;
    @Column(name="status")
    private long status;
}
