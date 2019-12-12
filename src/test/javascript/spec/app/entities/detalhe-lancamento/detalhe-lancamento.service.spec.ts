import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DetalheLancamentoService } from 'app/entities/detalhe-lancamento/detalhe-lancamento.service';
import { IDetalheLancamento, DetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

describe('Service Tests', () => {
  describe('DetalheLancamento Service', () => {
    let injector: TestBed;
    let service: DetalheLancamentoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDetalheLancamento;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DetalheLancamentoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DetalheLancamento(0, 0, 0, 0, 'AAAAAAA', currentDate, false, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT)
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

      it('should create a DetalheLancamento', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_TIME_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate,
            dataVencimento: currentDate
          },
          returnedFromService
        );
        service
          .create(new DetalheLancamento(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DetalheLancamento', () => {
        const returnedFromService = Object.assign(
          {
            valor: 1,
            desconto: 1,
            juro: 1,
            descricao: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            retencaoFonte: true,
            dataVencimento: currentDate.format(DATE_FORMAT),
            liquidado: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate,
            dataVencimento: currentDate
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

      it('should return a list of DetalheLancamento', () => {
        const returnedFromService = Object.assign(
          {
            valor: 1,
            desconto: 1,
            juro: 1,
            descricao: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            retencaoFonte: true,
            dataVencimento: currentDate.format(DATE_FORMAT),
            liquidado: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate,
            dataVencimento: currentDate
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

      it('should delete a DetalheLancamento', () => {
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
