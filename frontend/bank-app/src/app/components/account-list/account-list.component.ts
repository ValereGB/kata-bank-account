import { Component, OnInit } from '@angular/core';
import { Router, RouterModule, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/account.service';
import { AlertService } from '../../services/alert.service';
import { Account } from '../../models/account.model';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink]
})
export class AccountListComponent implements OnInit {
  accounts: Account[] = [];

  constructor(
    private accountService: AccountService,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.accountService.getAccounts().subscribe({
      next: (accounts) => {
        this.accounts = accounts;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des comptes:', error);
        this.alertService.show('danger', 'Erreur lors du chargement des comptes');
      }
    });
  }

  viewAccount(account: Account): void {
    this.router.navigate(['/accounts', account.accountNumber]);
  }

  createAccount(): void {
    this.router.navigate(['/accounts/new']);
  }

  deleteAccount(accountNumber: string): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce compte ?')) {
      this.accountService.deleteAccount(accountNumber).subscribe({
        next: () => {
          this.alertService.show('success', 'Compte supprimé avec succès');
          this.loadAccounts();
        },
        error: (error) => {
          console.error('Erreur lors de la suppression du compte:', error);
          this.alertService.show('danger', 'Erreur lors de la suppression du compte');
        }
      });
    }
  }
} 