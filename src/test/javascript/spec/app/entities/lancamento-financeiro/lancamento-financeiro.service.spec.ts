import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { LancamentoFinanceiroService } from 'app/entities/lancamento-financeiro/lancamento-financeiro.service';
import { ILancamentoFinanceiro, LancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';

describe('Service Tests', () => {
  describe('LancamentoFinanceiro Service', () => {
    let injector: TestBed;
    let service: LancamentoFinanceiroService;
    let httpMock: HttpTestingController;
    let elemDefault: ILancamentoFinanceiro;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LancamentoFinanceiroService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new LancamentoFinanceiro(0, 'AAAAAAA', 0, false, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a LancamentoFinanceiro', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new LancamentoFinanceiro(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a LancamentoFinanceiro', () => {
        const returnedFromService = Object.assign(
          {
            tipoLancamento: 'BBBBBB',
            valor: 1,
            externo: true,
            numero: 'BBBBBB',
            descricao: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of LancamentoFinanceiro', () => {
        const returnedFromService = Object.assign(
          {
            tipoLancamento: 'BBBBBB',
            valor: 1,
            externo: true,
            numero: 'BBBBBB',
            descricao: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LancamentoFinanceiro', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
