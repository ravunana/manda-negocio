import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DetalheAduaneiroService } from 'app/entities/detalhe-aduaneiro/detalhe-aduaneiro.service';
import { IDetalheAduaneiro, DetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';

describe('Service Tests', () => {
  describe('DetalheAduaneiro Service', () => {
    let injector: TestBed;
    let service: DetalheAduaneiroService;
    let httpMock: HttpTestingController;
    let elemDefault: IDetalheAduaneiro;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DetalheAduaneiroService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DetalheAduaneiro(0, 'AAAAAAA', 0, 0, 0, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataFabrico: currentDate.format(DATE_FORMAT),
            dataExpiracao: currentDate.format(DATE_FORMAT)
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

      it('should create a DetalheAduaneiro', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataFabrico: currentDate.format(DATE_FORMAT),
            dataExpiracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataFabrico: currentDate,
            dataExpiracao: currentDate
          },
          returnedFromService
        );
        service
          .create(new DetalheAduaneiro(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DetalheAduaneiro', () => {
        const returnedFromService = Object.assign(
          {
            numeroONU: 'BBBBBB',
            largura: 1,
            altura: 1,
            pesoLiquido: 1,
            pesoBruto: 1,
            dataFabrico: currentDate.format(DATE_FORMAT),
            dataExpiracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataFabrico: currentDate,
            dataExpiracao: currentDate
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

      it('should return a list of DetalheAduaneiro', () => {
        const returnedFromService = Object.assign(
          {
            numeroONU: 'BBBBBB',
            largura: 1,
            altura: 1,
            pesoLiquido: 1,
            pesoBruto: 1,
            dataFabrico: currentDate.format(DATE_FORMAT),
            dataExpiracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataFabrico: currentDate,
            dataExpiracao: currentDate
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

      it('should delete a DetalheAduaneiro', () => {
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
