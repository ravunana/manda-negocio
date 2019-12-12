import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SerieDocumentoService } from 'app/entities/serie-documento/serie-documento.service';
import { ISerieDocumento, SerieDocumento } from 'app/shared/model/serie-documento.model';

describe('Service Tests', () => {
  describe('SerieDocumento Service', () => {
    let injector: TestBed;
    let service: SerieDocumentoService;
    let httpMock: HttpTestingController;
    let elemDefault: ISerieDocumento;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SerieDocumentoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SerieDocumento(0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a SerieDocumento', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .create(new SerieDocumento(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a SerieDocumento', () => {
        const returnedFromService = Object.assign(
          {
            serie: 'BBBBBB',
            codigoLancamentoFinanceiro: 1,
            codigoEscrituracaoContabil: 1,
            codigoVenda: 1,
            codigoCompra: 1,
            codigoCliente: 1,
            codigoFornecedor: 1,
            codigoDevolucaoVenda: 1,
            codigoDevolucaoCompra: 1,
            codigoProduto: 1,
            data: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of SerieDocumento', () => {
        const returnedFromService = Object.assign(
          {
            serie: 'BBBBBB',
            codigoLancamentoFinanceiro: 1,
            codigoEscrituracaoContabil: 1,
            codigoVenda: 1,
            codigoCompra: 1,
            codigoCliente: 1,
            codigoFornecedor: 1,
            codigoDevolucaoVenda: 1,
            codigoDevolucaoCompra: 1,
            codigoProduto: 1,
            data: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
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

      it('should delete a SerieDocumento', () => {
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
