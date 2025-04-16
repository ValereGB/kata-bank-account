import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountService } from '../../services/account.service';
import { AlertService } from '../../services/alert.service';
import { Account } from '../../models/account.model';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, RouterLink]
})
export class AccountDetailsComponent implements OnInit {
  account: Account | null = null;
  amount: number = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    const accountNumber = this.route.snapshot.paramMap.get('id');
    if (accountNumber) {
      this.loadAccount(accountNumber);
    }
  }

  loadAccount(accountNumber: string): void {
    this.accountService.getAccount(accountNumber).subscribe({
      next: (account) => {
        this.account = account;
      },
      error: (error) => {
        console.error('Erreur lors du chargement du compte:', error);
        this.alertService.show('danger', 'Erreur lors du chargement du compte');
      }
    });
  }

  deposit(): void {
    if (!this.account?.accountNumber) {
      this.alertService.show('warning', 'Compte non trouvé');
      return;
    }

    if (this.amount <= 0) {
      this.alertService.show('warning', 'Le montant doit être supérieur à 0');
      return;
    }

    this.accountService.deposit(this.account.accountNumber, this.amount).subscribe({
      next: () => {
        this.alertService.show('success', 'Dépôt effectué avec succès');
        this.loadAccount(this.account!.accountNumber);
        this.amount = 0;
      },
      error: (error) => {
        console.error('Erreur lors du dépôt:', error);
        this.alertService.show('danger', 'Erreur lors du dépôt');
      }
    });
  }

  withdraw(): void {
    if (!this.account?.accountNumber) {
      this.alertService.show('warning', 'Compte non trouvé');
      return;
    }

    if (this.amount <= 0) {
      this.alertService.show('warning', 'Le montant doit être supérieur à 0');
      return;
    }

    if (this.amount > (this.account.balance || 0)) {
      this.alertService.show('danger', 'Solde insuffisant');
      return;
    }

    this.accountService.withdraw(this.account.accountNumber, this.amount).subscribe({
      next: () => {
        this.alertService.show('success', 'Retrait effectué avec succès');
        this.loadAccount(this.account!.accountNumber);
        this.amount = 0;
      },
      error: (error) => {
        console.error('Erreur lors du retrait:', error);
        this.alertService.show('danger', 'Erreur lors du retrait');
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/accounts']);
  }
} 