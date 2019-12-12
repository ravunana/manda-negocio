import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { GrupoTributacaoImpostoService } from 'app/entities/grupo-tributacao-imposto/grupo-tributacao-imposto.service';
import { IGrupoTributacaoImposto, GrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';

describe('Service Tests', () => {
  describe('GrupoTributacaoImposto Service', () => {
    let injector: TestBed;
    let service: GrupoTributacaoImpostoService;
    let httpMock: HttpTestingController;
    let elemDefault: IGrupoTributacaoImposto;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GrupoTributacaoImpostoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new GrupoTributacaoImposto(0, 'AAAAAAA', 0, 0, 0, 0);
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

      it('should create a GrupoTributacaoImposto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new GrupoTributacaoImposto(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a GrupoTributacaoImposto', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            valor: 1,
            de: 1,
            ate: 1,
            limiteLiquidacao: 1
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

      it('should return a list of GrupoTributacaoImposto', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            valor: 1,
            de: 1,
            ate: 1,
            limiteLiquidacao: 1
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

      it('should delete a GrupoTributacaoImposto', () => {
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
