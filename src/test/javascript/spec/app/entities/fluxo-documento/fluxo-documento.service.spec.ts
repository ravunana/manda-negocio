import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { FluxoDocumentoService } from 'app/entities/fluxo-documento/fluxo-documento.service';
import { IFluxoDocumento, FluxoDocumento } from 'app/shared/model/fluxo-documento.model';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

describe('Service Tests', () => {
  describe('FluxoDocumento Service', () => {
    let injector: TestBed;
    let service: FluxoDocumentoService;
    let httpMock: HttpTestingController;
    let elemDefault: IFluxoDocumento;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FluxoDocumentoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FluxoDocumento(0, 'AAAAAAA', false, false, 'AAAAAAA', false, 'AAAAAAA', EntidadeSistema.SOFTWARE);
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

      it('should create a FluxoDocumento', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new FluxoDocumento(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a FluxoDocumento', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            aumentaEstoque: true,
            aumentaValorCaixa: true,
            cor: 'BBBBBB',
            padrao: true,
            descricao: 'BBBBBB',
            entidade: 'BBBBBB'
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

      it('should return a list of FluxoDocumento', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            aumentaEstoque: true,
            aumentaValorCaixa: true,
            cor: 'BBBBBB',
            padrao: true,
            descricao: 'BBBBBB',
            entidade: 'BBBBBB'
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

      it('should delete a FluxoDocumento', () => {
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
