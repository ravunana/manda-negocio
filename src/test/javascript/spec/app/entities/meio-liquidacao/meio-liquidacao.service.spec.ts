import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { MeioLiquidacaoService } from 'app/entities/meio-liquidacao/meio-liquidacao.service';
import { IMeioLiquidacao, MeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';

describe('Service Tests', () => {
  describe('MeioLiquidacao Service', () => {
    let injector: TestBed;
    let service: MeioLiquidacaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IMeioLiquidacao;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MeioLiquidacaoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new MeioLiquidacao(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
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

      it('should create a MeioLiquidacao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new MeioLiquidacao(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a MeioLiquidacao', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            sigla: 'BBBBBB',
            icon: 'BBBBBB',
            mostrarPontoVenda: true
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

      it('should return a list of MeioLiquidacao', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            sigla: 'BBBBBB',
            icon: 'BBBBBB',
            mostrarPontoVenda: true
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

      it('should delete a MeioLiquidacao', () => {
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
