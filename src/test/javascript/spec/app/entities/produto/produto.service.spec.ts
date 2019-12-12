import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { IProduto, Produto } from 'app/shared/model/produto.model';

describe('Service Tests', () => {
  describe('Produto Service', () => {
    let injector: TestBed;
    let service: ProdutoService;
    let httpMock: HttpTestingController;
    let elemDefault: IProduto;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProdutoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Produto(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        false,
        0,
        0,
        0,
        'AAAAAAA',
        false,
        'AAAAAAA'
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

      it('should create a Produto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Produto(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Produto', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            ean: 'BBBBBB',
            numero: 'BBBBBB',
            nome: 'BBBBBB',
            imagem: 'BBBBBB',
            composto: true,
            estoqueMinimo: 1,
            estoqueMaximo: 1,
            estoqueAtual: 1,
            descricao: 'BBBBBB',
            mostrarPDV: true,
            prazoMedioEntrega: 'BBBBBB'
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

      it('should return a list of Produto', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            ean: 'BBBBBB',
            numero: 'BBBBBB',
            nome: 'BBBBBB',
            imagem: 'BBBBBB',
            composto: true,
            estoqueMinimo: 1,
            estoqueMaximo: 1,
            estoqueAtual: 1,
            descricao: 'BBBBBB',
            mostrarPDV: true,
            prazoMedioEntrega: 'BBBBBB'
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

      it('should delete a Produto', () => {
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
