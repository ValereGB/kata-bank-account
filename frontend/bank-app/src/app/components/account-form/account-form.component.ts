import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, RouterLink } from '@angular/router';
import { AccountService } from '../../services/account.service';
import { AlertService } from '../../services/alert.service';

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, RouterLink]
})
export class AccountFormComponent {
  accountNumber: string = '';
  owner: string = '';

  constructor(
    private accountService: AccountService,
    private router: Router,
    private alertService: AlertService
  ) { }

  onSubmit(): void {
    console.log('Form submitted with:', {
      accountNumber: this.accountNumber,
      owner: this.owner
    });
    
    this.accountService.createAccount(this.accountNumber, this.owner).subscribe({
      next: () => {
        this.alertService.show('success', 'Compte créé avec succès !');
        this.router.navigate(['/accounts']);
      },
      error: (error) => {
        console.error('Error creating account:', error);
        if (error.status === 400) {
          this.alertService.show('danger', 'Ce numéro de compte existe déjà');
        } else {
          this.alertService.show('danger', 'Une erreur est survenue lors de la création du compte');
        }
      }
    });
  }
} 