export interface Transaction {
  id: number;
  date: Date;
  type: 'DEPOSIT' | 'WITHDRAWAL';
  amount: number;
  description: string;
}

export interface Account {
  id?: number;
  accountNumber: string;
  owner: string;
  balance: number;
  transactions: Transaction[];
} 