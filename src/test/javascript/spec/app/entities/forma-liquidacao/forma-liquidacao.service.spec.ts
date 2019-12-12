import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { FormaLiquidacaoService } from 'app/entities/forma-liquidacao/forma-liquidacao.service';
import { IFormaLiquidacao, FormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';

describe('Service Tests', () => {
  describe('FormaLiquidacao Service', () => {
    let injector: TestBed;
    let service: FormaLiquidacaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IFormaLiquidacao;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FormaLiquidacaoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FormaLiquidacao(0, 'AAAAAAA', 0, 0, 0, 'AAAAAAA');
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

      it('should create a FormaLiquidacao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new FormaLiquidacao(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a FormaLiquidacao', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            juro: 1,
            prazoLiquidacao: 1,
            quantidade: 1,
            icon: 'BBBBBB'
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

      it('should return a list of FormaLiquidacao', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            juro: 1,
            prazoLiquidacao: 1,
            quantidade: 1,
            icon: 'BBBBBB'
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

      it('should delete a FormaLiquidacao', () => {
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
