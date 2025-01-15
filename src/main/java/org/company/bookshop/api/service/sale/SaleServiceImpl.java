package org.company.bookshop.api.service.sale;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.company.bookshop.api.dto.sale.SaleCustomerResponseDTO;
import org.company.bookshop.api.dto.sale.SaleRequestDTO;
import org.company.bookshop.api.dto.sale.SaleResponseDTO;
import org.company.bookshop.api.entity.BookEntity;
import org.company.bookshop.api.entity.ProfileEntity;
import org.company.bookshop.api.entity.SaleEntity;
import org.company.bookshop.api.exception.BadRequestException;
import org.company.bookshop.api.repository.SaleRepository;
import org.company.bookshop.api.service.book.BookServiceImpl;
import org.company.bookshop.api.service.profile.ProfileServiceImpl;
import org.company.bookshop.api.util.ApiResponse;
import org.company.bookshop.api.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SaleServiceImpl implements SaleService {

    SaleRepository saleRepository;
    BookServiceImpl bookServiceImpl;
    ProfileServiceImpl profileServiceImpl;

    @Override
    public ApiResponse<SaleResponseDTO> save(SaleRequestDTO request) {
        BookEntity book = bookServiceImpl.getById(request.bookId());
        ProfileEntity customer = profileServiceImpl.getById(SecurityUtil.getProfileId());
        if (customer.getBalance() < book.getPrice())
            throw new BadRequestException("Balance is not enough");
        SaleEntity entity = saleRepository.save(
                SaleEntity.builder()
                        .bookId(request.bookId())
                        .customerId(customer.getId())
                        .build());
        bookServiceImpl.updateCount(request.bookId(), book.getCount() - 1);
        profileServiceImpl.updateBalance(customer.getId(), customer.getBalance() - book.getPrice());
        return ApiResponse.ok(mapToResponse().apply(entity));
    }


    @Override
    public ApiResponse<SaleResponseDTO> findById(String saleId) {
        return ApiResponse.ok(mapToResponse().apply(getById(saleId)));
    }

    @Override
    public ApiResponse<List<SaleResponseDTO>> findAll() {
        return ApiResponse.ok(saleRepository.findAllByVisibleTrue().stream().map(mapToResponse()).toList());
    }

    @Override
    public void delete(String saleId) {
        saleRepository.delete(saleId);
    }

    @Override
    public ApiResponse<List<SaleCustomerResponseDTO>> myPurchased() {
        ProfileEntity profile = profileServiceImpl.getById(SecurityUtil.getProfileId());
        List<SaleCustomerResponseDTO> booklist
                = saleRepository.findAllBooksByCustomerIdAndVisibleTrue(profile.getId())
                .stream().map(saleBookMapper ->
                        SaleCustomerResponseDTO.builder()
                                .bookTitle(saleBookMapper.getBookTitle())
                                .bookPrice(saleBookMapper.getBookPrice())
                                .saleDate(saleBookMapper.getSaleDate().toLocalDate())
                                .build())
                .toList();
        return ApiResponse.ok(booklist);
    }

    private Function<SaleEntity, SaleResponseDTO> mapToResponse() {
        return entity -> SaleResponseDTO.builder()
                .bookId(entity.getBookId())
                .customerId(entity.getCustomerId())
                .build();
    }

    private SaleEntity getById(String saleId) {
        return saleRepository.findByIdAndVisibleTrue(saleId)
                .orElseThrow(() -> new BadRequestException("Sale not found"));
    }
}
