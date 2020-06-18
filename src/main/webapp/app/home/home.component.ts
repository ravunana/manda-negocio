import { ILancamentoFinanceiro } from './../shared/model/lancamento-financeiro.model';
import { LancamentoFinanceiroService } from './../entities/lancamento-financeiro/lancamento-financeiro.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

// import { ExportAsService, ExportAsConfig, SupportedExtensions } from 'ngx-export-as';

@Component({
  selector: 'rv-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account;
  authSubscription: Subscription;
  modalRef: NgbModalRef;
  lancamentos: ILancamentoFinanceiro[] = [];

  title = 'Browser market shares at a specific website, 2014';
  type = 'PieChart';
  data = [
    // ['Opera', 12.4]
  ];
  columnNames = ['ENTRADA', 'VALOR'];
  options = {};
  width = 650;
  height = 450;

  // config: ExportAsConfig = {
  //   type: 'pdf',
  //   elementId: 'mytable',
  //   options: {
  //     jsPDF: {
  //       orientation: 'landscape'
  //     },
  //     pdfCallbackFn: this.pdfCallbackFn // to add header and footer
  //   }
  // };

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    protected lancamentoFinanceiroService: LancamentoFinanceiroService // private exportAsService: ExportAsService
  ) {}

  ngOnInit() {
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });

    // this.data.push(['RV', 87]);

    this.registerAuthenticationSuccess();
    this.lancamentoFinanceiroService.query().subscribe(lancamentos => {
      this.lancamentos = lancamentos.body;
      this.lancamentos.map((m: ILancamentoFinanceiro) => {
        this.data.push([m.contaDescricao, m.valor]);
      });
    });
  }

  registerAuthenticationSuccess() {
    this.authSubscription = this.eventManager.subscribe('authenticationSuccess', () => {
      this.accountService.identity().subscribe(account => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.eventManager.destroy(this.authSubscription);
    }
  }

  // exportAs(type: SupportedExtensions, opt?: string) {
  //   this.config.type = type;
  //   if (opt) {
  //     this.config.options.jsPDF.orientation = opt;
  //   }
  //   this.exportAsService.save(this.config, 'myFile').subscribe(() => {
  //     // save started
  //   });
  //   this.exportAsService.get(this.config).subscribe(content => {
  //     const link = document.createElement('a');
  //     const fileName = 'export.pdf';

  //     link.href = content;
  //     link.download = fileName;
  //     link.click();
  //     console.log(content);
  //   });
  // }

  // pdfCallbackFn(pdf: any) {
  //   // example to add page number as footer to every page of pdf
  //   const noOfPages = pdf.internal.getNumberOfPages();
  //   for (let i = 1; i <= noOfPages; i++) {
  //     pdf.setPage(i);
  //     pdf.text('Page ' + i + ' of ' + noOfPages, pdf.internal.pageSize.getWidth() - 100, pdf.internal.pageSize.getHeight() - 30);
  //   }
  // }
}
