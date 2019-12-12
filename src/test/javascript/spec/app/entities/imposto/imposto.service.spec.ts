import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ImpostoService } from 'app/entities/imposto/imposto.service';
import { IImposto, Imposto } from 'app/shared/model/imposto.model';

describe('Service Tests', () => {
  describe('Imposto Service', () => {
    let injector: TestBed;
    let service: ImpostoService;
    let httpMock: HttpTestingController;
    let elemDefault: IImposto;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ImpostoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Imposto(0, 'AAAAAAA', 'AAAAAAA', false, 0, 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vigencia: currentDate.format(DATE_FORMAT)
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

      it('should create a Imposto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vigencia: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            vigencia: currentDate
          },
          returnedFromService
        );
        service
          .create(new Imposto(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Imposto', () => {
        const returnedFromService = Object.assign(
          {
            tipoImposto: 'BBBBBB',
            codigoImposto: 'BBBBBB',
            porcentagem: true,
            valor: 1,
            descricao: 'BBBBBB',
            pais: 'BBBBBB',
            vigencia: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vigencia: currentDate
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

      it('should return a list of Imposto', () => {
        const returnedFromService = Object.assign(
          {
            tipoImposto: 'BBBBBB',
            codigoImposto: 'BBBBBB',
            porcentagem: true,
            valor: 1,
            descricao: 'BBBBBB',
            pais: 'BBBBBB',
            vigencia: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            vigencia: currentDate
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

      it('should delete a Imposto', () => {
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
