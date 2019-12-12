import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DocumentoComercialService } from 'app/entities/documento-comercial/documento-comercial.service';
import { IDocumentoComercial, DocumentoComercial } from 'app/shared/model/documento-comercial.model';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

describe('Service Tests', () => {
  describe('DocumentoComercial Service', () => {
    let injector: TestBed;
    let service: DocumentoComercialService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocumentoComercial;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DocumentoComercialService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DocumentoComercial(
        0,
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        false,
        false,
        'AAAAAAA',
        'AAAAAAA',
        false,
        EntidadeSistema.SOFTWARE
      );
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

      it('should create a DocumentoComercial', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DocumentoComercial(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DocumentoComercial', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            serie: 'BBBBBB',
            padrao: true,
            movimentaEstoque: true,
            movimentaCaixa: true,
            movimentaContabilidade: true,
            cor: 'BBBBBB',
            descricao: 'BBBBBB',
            mostraPontoVenda: true,
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

      it('should return a list of DocumentoComercial', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            serie: 'BBBBBB',
            padrao: true,
            movimentaEstoque: true,
            movimentaCaixa: true,
            movimentaContabilidade: true,
            cor: 'BBBBBB',
            descricao: 'BBBBBB',
            mostraPontoVenda: true,
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

      it('should delete a DocumentoComercial', () => {
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
