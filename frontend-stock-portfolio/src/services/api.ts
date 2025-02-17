import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

export interface Stock {
    id: string;
    name: string;
    symbol: string;
    currentPrice: number;
}

export interface StockHolding {
    stock: Stock;
    sharesOwned: number;
    purchasePrice: number;
}

export interface Portfolio {
    id: string;
    owner: string;
    holdings: StockHolding[];
}

export const getPortfolio = async (name: string): Promise<Portfolio | null> => {
    console.log(`Fetching portfolio for: ${name}`);

    try {
        const response = await axios.get(`${API_URL}/portfolios/${name}`);
        console.log('Portfolio fetched:', response.data);
        return response.data;
    } catch (error: any) {
        if (error.response) {
            console.error(`API Error (${error.response.status}):`, error.response.data);
        } else {
            console.error('Network or CORS error:', error.message);
        }

        // If portfolio is not found, attempt to create one
        if (error.response?.status === 404) {
            console.log(`Portfolio not found. Creating new portfolio for: ${name}`);
            try {
                const postResponse = await axios.post(`${API_URL}/portfolios/${name}`);
                console.log('Portfolio created:', postResponse.data); // Debug log
                return postResponse.data;
            } catch (postError) {
                console.error('Error creating portfolio:', postError);
                return null;
            }
        }

        return null;
    }
};

export const getStocks = async (): Promise<Stock[]> => {
    try {
        const response = await axios.get(`${API_URL}/stocks`);
        return response.data;
    } catch (error) {
        console.error('Error fetching stocks:', error);
        return [];
    }
};

export const addStockToPortfolio = async (portfolioId: string, stockSymbol: string, shares: number) => {
    try {
        const response = await axios.post(`${API_URL}/portfolios/${portfolioId}/stocks`, null, {
            params: { stockSymbol, shares}
        });
        return response.data;
    } catch (error) {
        console.error('Error adding stock:', error);
        return null;
    }
};


export const updatePortfolioPrices = async (portfolioId: string) => {
    try {
        const response = await axios.post(`${API_URL}/portfolios/${portfolioId}/stocks/price`);
        return response.data;
    } catch (error) {
        console.error('Error fetching update:', error);
        return null;
    }
};

