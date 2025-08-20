package com.enset.digital_banking_tp4_backend.entities;

import com.enset.digital_banking_tp4_backend.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


import java.sql.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length=2)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount   {
    @Id
    private String id;
    @NotEmpty @Min(0)
    private double balance;
    @NotEmpty
    private Date creditedAt;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @NotEmpty
    private String currency;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
    private List<AccountOperations> accountOperationList;

}
