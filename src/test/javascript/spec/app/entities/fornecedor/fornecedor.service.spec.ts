import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';
import { IFornecedor, Fornecedor } from 'app/shared/model/fornecedor.model';

describe('Service Tests', () => {
  describe('Fornecedor Service', () => {
    let injector: TestBed;
    let service: FornecedorService;
    let httpMock: HttpTestingController;
    let elemDefault: IFornecedor;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FornecedorService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Fornecedor(0, false, false, false, 0, false, 'AAAAAAA', 0, 'AAAAAAA', false, 'AAAAAAA');
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

      it('should create a Fornecedor', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Fornecedor(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Fornecedor', () => {
        const returnedFromService = Object.assign(
          {
            certificadoISO9001: true,
            garantiaDefeitoFabrica: true,
            transporte: true,
            qualidade: 1,
            pagamentoPrazo: true,
            metodosPagamento: 'BBBBBB',
            classificacao: 1,
            descricao: 'BBBBBB',
            ativo: true,
            numero: 'BBBBBB'
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

      it('should return a list of Fornecedor', () => {
        const returnedFromService = Object.assign(
          {
            certificadoISO9001: true,
            garantiaDefeitoFabrica: true,
            transporte: true,
            qualidade: 1,
            pagamentoPrazo: true,
            metodosPagamento: 'BBBBBB',
            classificacao: 1,
            descricao: 'BBBBBB',
            ativo: true,
            numero: 'BBBBBB'
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

      it('should delete a Fornecedor', () => {
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
